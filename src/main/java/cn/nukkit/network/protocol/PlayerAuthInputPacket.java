package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector2f;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.types.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@ToString
@Setter
@Getter
public class PlayerAuthInputPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_AUTH_INPUT_PACKET;

    private float yaw;
    private float pitch;
    private float headYaw;
    private Vector3f position;
    private Vector2 motion;
    private Set<AuthInputAction> inputData = EnumSet.noneOf(AuthInputAction.class);
    private InputMode inputMode;
    private ClientPlayMode playMode;
    private AuthInteractionModel interactionModel;
    /**
     * @deprecated since v748
     */
    private Vector3f vrGazeDirection;
    private long tick;
    private Vector3f delta;
    // private ItemStackRequest itemStackRequest;
    private Map<PlayerActionType, PlayerBlockActionData> blockActionData = new EnumMap<>(PlayerActionType.class);
    /**
     * @since v748
     */
    private Vector2f interactRotation;
    /**
     * @since 575
     */
    private Vector2f analogMoveVector;
    /**
     * @since 649
     */
    private long predictedVehicle;
    /**
     * @since v662 1.20.70
     */
    private Vector2f vehicleRotation;
    /**
     * @since v748
     */
    private Vector3f cameraOrientation;
    /**
     * @since v766
     */
    private Vector2f rawMoveVector;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.pitch = this.getLFloat();
        this.yaw = this.getLFloat();
        this.position = this.getVector3f();
        this.motion = new Vector2(this.getLFloat(), this.getLFloat());
        this.headYaw = this.getLFloat();

        long inputData = this.getUnsignedVarLong();
        for (int i = 0; i < AuthInputAction.size(); i++) {
            if ((inputData & (1L << i)) != 0) {
                this.inputData.add(AuthInputAction.from(i));
            }
        }

        // some cheat clients can mess this data and crash movement
        int inputModeOrdinal = (int) this.getUnsignedVarInt();
        if (inputModeOrdinal >= 0 && inputModeOrdinal < InputMode.values().length) {
            this.inputMode = InputMode.values()[inputModeOrdinal];
        } else {
            this.inputMode = InputMode.UNDEFINED;
        }

        int playModeOrdinal = (int) this.getUnsignedVarInt();
        this.playMode = ClientPlayMode.values()[
                (playModeOrdinal >= 0 && playModeOrdinal < ClientPlayMode.values().length)
                        ? playModeOrdinal : 0
                ];

        int interactionModelOrdinal = (int) this.getUnsignedVarInt();
        if (interactionModelOrdinal >= 0 && interactionModelOrdinal < AuthInteractionModel.values().length) {
            this.interactionModel = AuthInteractionModel.values()[interactionModelOrdinal];
        } else {
            this.interactionModel = AuthInteractionModel.TOUCH;
        }

        if (protocol >= ProtocolInfo.v1_21_40) {
            this.interactRotation = this.getVector2f();
        } else {
            if (this.playMode == ClientPlayMode.REALITY) {
                this.vrGazeDirection = this.getVector3f();
            }
        }

        this.tick = this.getUnsignedVarLong();
        this.delta = this.getVector3f();

        if (this.inputData.contains(AuthInputAction.PERFORM_BLOCK_ACTIONS)) {
            int arraySize = this.getVarInt();
            if (arraySize > 256 || arraySize < 0) {
                throw new IllegalArgumentException("Invalid block actions size: " + arraySize);
            }
            for (int i = 0; i < arraySize; i++) {
                int actionTypeOrdinal = this.getVarInt();
                PlayerActionType type;
                if (actionTypeOrdinal >= 0 && actionTypeOrdinal < PlayerActionType.values().length) {
                    type = PlayerActionType.values()[actionTypeOrdinal];
                } else {
                    continue;
                }

                switch (type) {
                    case START_DESTROY_BLOCK:
                    case ABORT_DESTROY_BLOCK:
                    case CRACK_BLOCK:
                    case PREDICT_DESTROY_BLOCK:
                    case CONTINUE_DESTROY_BLOCK:
                        this.blockActionData.put(type, new PlayerBlockActionData(type, this.getSignedBlockPosition(), this.getVarInt()));
                        break;
                    default:
                        this.blockActionData.put(type, new PlayerBlockActionData(type, null, -1));
                }
            }
        }

        if (protocol >= ProtocolInfo.v1_20_60 && this.inputData.contains(AuthInputAction.IN_CLIENT_PREDICTED_IN_VEHICLE)) {
            if (protocol >= ProtocolInfo.v1_20_70) {
                this.vehicleRotation = this.getVector2f();
            }
            this.predictedVehicle = this.getVarLong();
        }

        this.analogMoveVector = this.getVector2f();

        if (protocol >= ProtocolInfo.v1_21_40) {
            this.cameraOrientation = this.getVector3f();
        }
        if (protocol >= ProtocolInfo.v1_21_50) {
            this.rawMoveVector = this.getVector2f();
        }
    }

    @Override
    public void encode() {
        // Noop
    }
}