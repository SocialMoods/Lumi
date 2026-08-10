package cn.nukkit.entity.data.skin;

import lombok.ToString;

import java.util.UUID;

/**
 * Persona skin piece
 */
@ToString
public class PersonaPiece {

    public final String id;
    public final PersonaPieceType type;
    public final UUID packId;
    public final boolean isDefault;
    public final String productId;

    public PersonaPiece(String id, String type, String packId, boolean isDefault, String productId) {
        this(id, PersonaPieceType.fromName(type), parsePackId(packId), isDefault, productId);
    }

    public PersonaPiece(String id, PersonaPieceType type, UUID packId, boolean isDefault, String productId) {
        this.id = id;
        this.type = type;
        this.packId = packId;
        this.isDefault = isDefault;
        this.productId = productId;
    }

    private static UUID parsePackId(String packId) {
        if (packId == null || packId.isEmpty()) {
            return new UUID(0, 0);
        }
        try {
            return UUID.fromString(packId);
        } catch (IllegalArgumentException ignored) {
            return new UUID(0, 0);
        }
    }
}
