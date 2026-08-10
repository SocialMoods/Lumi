package cn.nukkit.entity.data.skin;

import com.google.common.collect.ImmutableList;
import lombok.ToString;

import java.util.List;

/**
 * Persona skin piece tint
 */
@ToString
public class PersonaPieceTint {

    public final PersonaPieceType pieceType;
    public final ImmutableList<String> colors;

    public PersonaPieceTint(String pieceType, List<String> colors) {
        this(PersonaPieceType.fromName(pieceType), colors);
    }

    public PersonaPieceTint(PersonaPieceType pieceType, List<String> colors) {
        this.pieceType = pieceType;
        this.colors = ImmutableList.copyOf(colors);
    }
}
