package kyh.textadventure;

public class Key extends Item{

    private String unlockDoorDirection;
    private Room roomToUnlock;

    public Key(String inType, String inDescription, String direction, Room room) {
        super(inType, inDescription);
        unlockDoorDirection = direction;
        roomToUnlock = room;
    }

    public String getUnlockDoorDirection(){
        return unlockDoorDirection;
    }

    public Room getRoomToUnlock(){
        return roomToUnlock;
    }

}
