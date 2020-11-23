package entity.tile;

public enum Activity {
    GO_TO_JAIL,
    CHANCE,
    COMMUNITY_CHEST,
    FREE_PARK_VISIT
}
/*
 remove GAIN as there is no such tile with activity GAIN
 in other words, no tile directly gives a player money when
 the player lands on it.
*/
