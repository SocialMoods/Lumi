package cn.nukkit.network.protocol;

import cn.nukkit.utils.TextFormat;
import lombok.ToString;

@ToString
public class TextPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.TEXT_PACKET;

    private static final int MAX_MESSAGE_CHARS = 65536;
    private static final int MAX_SOURCE_CHARS = 256;
    private static final int MAX_XUID_CHARS = 64;
    private static final int MAX_PLATFORM_CHAT_ID_CHARS = 256;
    private static final int MAX_PARAMETERS = 4;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public static final byte TYPE_RAW = 0;
    public static final byte TYPE_CHAT = 1;
    public static final byte TYPE_TRANSLATION = 2;
    public static final byte TYPE_POPUP = 3;
    public static final byte TYPE_JUKEBOX_POPUP = 4;
    public static final byte TYPE_TIP = 5;
    public static final byte TYPE_SYSTEM = 6;
    public static final byte TYPE_WHISPER = 7;
    public static final byte TYPE_ANNOUNCEMENT = 8;
    public static final byte TYPE_OBJECT = 9;
    public static final byte TYPE_OBJECT_WHISPER = 10;
    /**
     * @since v553
     */
    public static final byte TYPE_OBJECT_ANNOUNCEMENT = 11;

    public byte type;
    public String source = "";
    public String message = " ";
    public String[] parameters = new String[0];
    public boolean isLocalized = false;
    public String xboxUserId = "";
    public String platformChatId = "";
    /**
     * @since v685
     */
    public String filteredMessage = "";

    @Override
    public void decode() {
        if (this.protocol < ProtocolInfo.v1_21_130) {
            this.type = (byte) getByte();
        }

        this.isLocalized = this.getBoolean() || type == TYPE_TRANSLATION;
        if (this.protocol < ProtocolInfo.v1_21_130) {
            switch (type) {
                case TYPE_CHAT:
                case TYPE_WHISPER:
                case TYPE_ANNOUNCEMENT:
                    this.source = this.getString(MAX_SOURCE_CHARS);
                case TYPE_RAW:
                case TYPE_TIP:
                case TYPE_SYSTEM:
                case TYPE_OBJECT:
                case TYPE_OBJECT_WHISPER:
                case TYPE_OBJECT_ANNOUNCEMENT:
                    this.message = this.getString(MAX_MESSAGE_CHARS);
                    break;

                case TYPE_TRANSLATION:
                case TYPE_POPUP:
                case TYPE_JUKEBOX_POPUP:
                    this.message = this.getString(MAX_MESSAGE_CHARS);
                    int count = (int) this.getUnsignedVarInt();
                    if (count > MAX_PARAMETERS) {
                        throw new IllegalArgumentException("Parameter List maxItems is " + MAX_PARAMETERS);
                    }
                    this.parameters = new String[count];
                    for (int i = 0; i < this.parameters.length; i++) {
                        this.parameters[i] = this.getString(MAX_MESSAGE_CHARS);
                    }
            }
        } else {
            switch (this.getByte()) {
                case 0: // MessageOnly
                    if(protocol < ProtocolInfo.v1_26_0) {
                        for (int i = 0; i < 6; i++) {
                            this.getString();
                        }
                    }
                    this.type = (byte) getByte();
                    this.message = this.getString(MAX_MESSAGE_CHARS);
                    break;
                case 1: // AuthorAndMessage
                    if(protocol < ProtocolInfo.v1_26_0) {
                        for (int i = 0; i < 3; i++) {
                            this.getString();
                        }
                    }
                    this.type = (byte) getByte();
                    this.source = this.getString(MAX_SOURCE_CHARS);
                    this.message = this.getString(MAX_MESSAGE_CHARS);
                    break;
                case 2: // MessageAndParams
                    if(protocol < ProtocolInfo.v1_26_0) {
                        for (int i = 0; i < 3; i++) {
                            this.getString();
                        }
                    }
                    this.type = (byte) getByte();
                    this.message = this.getString(MAX_MESSAGE_CHARS);
                    int paramCount = (int) this.getUnsignedVarInt();
                    if (paramCount > MAX_PARAMETERS) {
                        throw new IllegalArgumentException("Parameter List maxItems is " + MAX_PARAMETERS);
                    }
                    this.parameters = new String[paramCount];
                    for (int i = 0; i < this.parameters.length; i++) {
                        this.parameters[i] = this.getString(MAX_MESSAGE_CHARS);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Not oneOf<MessageOnly, AuthorAndMessage, MessageAndParams>");
            }
        }

        this.xboxUserId = this.getString(MAX_XUID_CHARS);
        this.platformChatId = this.getString(MAX_PLATFORM_CHAT_ID_CHARS);
        if (protocol >= ProtocolInfo.v1_21_130) {
            if (this.getBoolean()) {
                this.filteredMessage = this.getString(MAX_MESSAGE_CHARS);
            }
        } else if (protocol >= ProtocolInfo.v1_21_0) {
            this.filteredMessage = this.getString(MAX_MESSAGE_CHARS);
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.message = TextFormat.clamp(this.message, MAX_MESSAGE_CHARS);
        this.source = TextFormat.clamp(this.source, MAX_SOURCE_CHARS);
        this.filteredMessage = TextFormat.clamp(this.filteredMessage, MAX_MESSAGE_CHARS);

        if (this.protocol < ProtocolInfo.v1_21_130) {
            this.putByte(this.type);
        }

        this.putBoolean(this.isLocalized || type == TYPE_TRANSLATION);

        if (this.protocol < ProtocolInfo.v1_21_130) {
            switch (this.type) {
                case TYPE_CHAT:
                case TYPE_WHISPER:
                case TYPE_ANNOUNCEMENT:
                    this.putString(this.source);
                case TYPE_RAW:
                case TYPE_TIP:
                case TYPE_SYSTEM:
                case TYPE_OBJECT:
                case TYPE_OBJECT_WHISPER:
                case TYPE_OBJECT_ANNOUNCEMENT:
                    this.putString(this.message);
                    break;

                case TYPE_TRANSLATION:
                case TYPE_POPUP:
                case TYPE_JUKEBOX_POPUP:
                    this.putString(this.message);
                    this.putUnsignedVarInt(this.parameters.length);
                    for (String parameter : this.parameters) {
                        this.putString(parameter);
                    }
            }
        } else {
            switch (this.type) {
                case TYPE_RAW:
                case TYPE_TIP:
                case TYPE_SYSTEM:
                case TYPE_OBJECT:
                case TYPE_OBJECT_WHISPER:
                case TYPE_OBJECT_ANNOUNCEMENT:
                    this.putByte((byte) 0); // MessageOnly
                    if(protocol < ProtocolInfo.v1_26_0) {
                        this.putString("raw");
                        this.putString("tip");
                        this.putString("systemMessage");
                        this.putString("textObjectWhisper");
                        this.putString("textObjectAnnouncement");
                        this.putString("textObject");
                    }
                    this.putByte(this.type);
                    if (this.message.isEmpty()) {
                        this.message = " ";
                    }
                    this.putString(this.message);
                    break;

                case TYPE_CHAT:
                case TYPE_WHISPER:
                case TYPE_ANNOUNCEMENT:
                    this.putByte((byte) 1); // AuthorAndMessage
                    if(protocol < ProtocolInfo.v1_26_0) {
                        this.putString("chat");
                        this.putString("whisper");
                        this.putString("announcement");
                    }
                    this.putByte(this.type);
                    this.putString(this.source);
                    this.putString(this.message);
                    break;

                case TYPE_TRANSLATION:
                case TYPE_POPUP:
                case TYPE_JUKEBOX_POPUP:
                    this.putByte((byte) 2); // MessageAndParams
                    if(protocol < ProtocolInfo.v1_26_0) {
                        this.putString("translate");
                        this.putString("popup");
                        this.putString("jukeboxPopup");
                    }
                    this.putByte(this.type);
                    this.putString(this.message);
                    this.putUnsignedVarInt(this.parameters.length);
                    for (String parameter : this.parameters) {
                        this.putString(parameter);
                    }
            }
        }

        this.putString(this.xboxUserId);
        this.putString(this.platformChatId);

        if (protocol >= ProtocolInfo.v1_21_0) {
            if (protocol >= ProtocolInfo.v1_21_130) {
                this.putBoolean(!this.filteredMessage.isEmpty());
            }
            if (protocol < ProtocolInfo.v1_21_130 || !this.filteredMessage.isEmpty()) {
                this.putString(this.filteredMessage);
            }
        }
    }
}