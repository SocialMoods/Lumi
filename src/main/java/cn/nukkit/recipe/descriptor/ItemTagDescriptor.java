package cn.nukkit.recipe.descriptor;

import cn.nukkit.item.material.tags.ItemTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.BinaryStream;

public class ItemTagDescriptor extends ItemDescriptor {
    private final ItemTag itemTag;
    private final String id;

    public ItemTagDescriptor(ItemTag itemTag, String id) {
        this.itemTag = itemTag;
        this.id = id;
    }

    public ItemTag getItemTag() {
        return itemTag;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean putRecipe(BinaryStream stream, int protocol) {
        if(stream != null) {
            if (protocol >= ProtocolInfo.v1_26_40) {
                stream.putUnsignedVarInt(1);
                stream.putString("item_tag");
                stream.putString(id);
                stream.putVarInt(Short.MAX_VALUE);
            } else {
                stream.putByte((byte) 3);
                stream.putString(id);
            }
            stream.putVarInt(1);
        }
        return true;
    }
}
