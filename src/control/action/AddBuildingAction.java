package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Property;

public class AddBuildingAction implements Action {
    private Property property;
    private Player player;
    private boolean isHotel;

    public AddBuildingAction(Property property, Player player, boolean isHotel) {
        this.property = property;
        this.player = player;
        this.isHotel = isHotel;
    }

    @Override
    public void act() { // color check?
        if ( isHotel ) {
            if ( player.getBalance() > property.getHotelPrice() ) {
                player.takeMoney(property.getHotelPrice());
                property.addBuilding(hotel); // ???
            }
        }
        else {
            if ( player.getBalance() > property.getHousePrice() ) {
                player.takeMoney(property.getHousePrice());
                property.addBuilding(hotel); // ???
            }
        }
        player.takeMoney(isHotel ? property.getHotelPrice() : property.getHousePrice());
        property.addBuilding(); // ToDo implement the function
        MonopolyGame.getActionLog().addMessage(player.getName() + "adds building to " + property.getName());
    }

    /* addBuildingButton.addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
            addBuildingPanel.setVisible(false);
            new AddBuildingAction(monopolyGame.getBoard().getActiveTile() ,monopolyGame.getPlayerController().getActivePlayer(), false);

        }
    }

     */

}
