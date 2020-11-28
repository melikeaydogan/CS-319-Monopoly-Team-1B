package control.action;

import control.MonopolyGame;
import entity.Player;
import entity.property.Building;
import entity.property.Property;

public class AddHouseAction implements Action {
    private Building building;
    private Player player;

    public AddHouseAction(Building building, Player player) {
        this.building = building;
        this.player = player;
    }

    @Override
    public void act() { // color check?
        if ( player.getBalance() > building.getHousePrice() ) {
                new RemoveMoneyAction(player, building.getHousePrice()).act();
                building.addHouse(); // ???
        }

        MonopolyGame.getActionLog().addMessage(player.getName() + "adds a house to " + building.getName() + "\n");
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
