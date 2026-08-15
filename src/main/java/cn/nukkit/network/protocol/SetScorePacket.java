package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.ScorerType;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class SetScorePacket extends DataPacket {

    public Action action;
    public List<ScoreInfo> infos = new ArrayList<>();

    @Override
    public byte pid() {
        return ProtocolInfo.SET_SCORE_PACKET;
    }

    private static final String[] V2168_TYPE_NAMES = {"remove", "changeplayer", "changeentity", "changefakeplayer"};

    private static int v2168TypeOrdinal(Action action, ScorerType type) {
        if (action == Action.REMOVE) return 0;
        return switch (type) {
            case PLAYER -> 1;
            case ENTITY -> 2;
            case FAKE -> 3;
            default -> throw new IllegalArgumentException("Invalid score info received");
        };
    }
    @Override
    public void decode() {
        //only server -> client
    }

    @Override
    public void encode() {
        this.reset();
        if (this.protocol >= ProtocolInfo.v1_26_40) {
            this.putUnsignedVarInt(this.infos.size());
            for (ScoreInfo info : this.infos) {
                int typeOrdinal = v2168TypeOrdinal(this.action, info.type);
                this.putUnsignedVarInt(typeOrdinal);
                this.putString(V2168_TYPE_NAMES[typeOrdinal]);
                this.putVarLong(info.scoreboardId);
                switch (typeOrdinal) {
                    case 0 -> {
                        boolean present = info.objectiveId != null && !info.objectiveId.isEmpty();
                        this.putBoolean(present);
                        if (present) {
                            if (this.protocol >= ProtocolInfo.v1_26_44) {
                                this.putBoolean(true);
                            }
                            this.putString(info.objectiveId);
                        }
                    }
                    case 1, 2 -> {
                        this.putString(info.objectiveId == null || info.objectiveId.isEmpty() ? " " : info.objectiveId);
                        this.putLInt(info.score);
                        this.putVarLong(info.entityId);
                    }
                    case 3 -> {
                        this.putString(info.objectiveId == null || info.objectiveId.isEmpty() ? " " : info.objectiveId);
                        this.putLInt(info.score);
                        this.putString(info.name == null || info.name.isEmpty() ? " " : info.name);
                    }
                }
            }
            return;
        }
        this.putByte((byte) this.action.ordinal());
        this.putUnsignedVarInt(this.infos.size());
        for (ScoreInfo info : this.infos) {
            this.putVarLong(info.scoreboardId);
            this.putString(info.objectiveId);
            this.putLInt(info.score);
            if (this.action == Action.SET) {
                this.putByte((byte) info.type.ordinal());
                switch (info.type) {
                    case ENTITY, PLAYER -> this.putVarLong(info.entityId);
                    case FAKE -> this.putString(info.name);
                    default -> throw new IllegalArgumentException("Invalid score info received");
                }
            }
        }
    }
    public enum Action {
        SET,
        REMOVE
    }

    @ToString
    public static class ScoreInfo {
        public long scoreboardId;
        public String objectiveId;
        public int score;
        public ScorerType type;
        public String name;
        public long entityId;

        public ScoreInfo(long scoreboardId, String objectiveId, int score) {
            this.scoreboardId = scoreboardId;
            this.objectiveId = objectiveId;
            this.score = score;
            this.type = ScorerType.INVALID;
            this.name = null;
            this.entityId = -1;
        }

        public ScoreInfo(long scoreboardId, String objectiveId, int score, String name) {
            this.scoreboardId = scoreboardId;
            this.objectiveId = objectiveId;
            this.score = score;
            this.type = ScorerType.FAKE;
            this.name = name;
            this.entityId = -1;
        }

        public ScoreInfo(long scoreboardId, String objectiveId, int score, ScorerType type, long entityId) {
            this.scoreboardId = scoreboardId;
            this.objectiveId = objectiveId;
            this.score = score;
            this.type = type;
            this.entityId = entityId;
            this.name = null;
        }
    }
}