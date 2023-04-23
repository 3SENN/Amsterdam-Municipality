package nl.hva.amsserver.models.dto;

import java.util.Objects;

/**
 * Class represents a message
 * * <br/>
 * Is used to send messages with sockets with the specific format: userId - room - session
 */
public class Message {
    private long userId;
    private String room;
    private String session;

    /**
     * Instantiates a new Message.
     *
     * @param userId    The id of the user
     * @param room      The name of the room
     * @param session   The connected session
     */
    public Message(long userId, String room, String session) {
        this.userId = userId;
        this.room = room;
        this.session = session;
    }

    public Message(long userId, String room) {
        this.userId = userId;
        this.room = room;
    }


    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets room.
     *
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets room.
     *
     * @param room the room
     */
    public void setRoom(String room) {
        this.room = room;
    }


    public String getSession() {
        return session;
    }

    public boolean equals(Message message) {
        return Objects.equals(this.getRoom(), message.getRoom()) && this.getUserId() == message.getUserId();
    }

    public boolean roomEquals(Message message) {
        return Objects.equals(this.getRoom(), message.getRoom());
    }

    public boolean sessionIdEquals(String sessionId) {
        return Objects.equals(this.getSession(), sessionId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId=" + userId +
                ", room='" + room + '\'' +
                ", session='" + session + '\'' +
                '}';
    }
}
