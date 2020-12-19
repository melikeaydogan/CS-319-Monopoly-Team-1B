package gui;

import control.action.AddClassroomAction;
import control.action.AddLectureHallAction;
import entity.Player;
import entity.property.Building;
import entity.property.Dorm;
import entity.property.Facility;
import entity.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import network.MonopolyClient;

import java.text.DecimalFormat;
import java.util.Optional;

public class PropertyMenuController {
    MonopolyClient client;
    Property property;
    GameScreenController gameScreenController;
    Player player;

    @FXML Button addClassroomButton;
    @FXML Button addLectureHallButton;
    @FXML Button mortgageButton;
    @FXML Text text;

    @FXML
    protected void addClassroomUsed(ActionEvent e) {
        if (client.getMonopolyGame().getActivePlayer().getPlayerId() == player.getPlayerId()) {
            boolean classRoomPurchased = showAddClassroomDialog((Building) property);

            if (classRoomPurchased) {
                AddClassroomAction addClassroomAction = new AddClassroomAction(property.getId(),
                        client.getMonopolyGame().getActivePlayer().getPlayerId());
                addClassroomAction.act();
                gameScreenController.sendAction(addClassroomAction);
                gameScreenController.updateBoardState();
            }
        }
        updateMenu();
    }

    @FXML
    protected void addLectureHallUsed(ActionEvent e) {
        if (client.getMonopolyGame().getActivePlayer().getPlayerId() == player.getPlayerId()) {
            boolean lectureHallPurchased = showAddHotelDialog((Building) property);

            if (lectureHallPurchased) {
                AddLectureHallAction addLectureHallAction = new AddLectureHallAction(property.getId(),
                        client.getMonopolyGame().getActivePlayer().getPlayerId());
                addLectureHallAction.act();
                gameScreenController.sendAction(addLectureHallAction);
                gameScreenController.updateBoardState();
            }
        }
        updateMenu();
    }


    @FXML void mortgageUsed(ActionEvent e) {
        // TODO
        updateMenu();
    }

    public void setupPropertyMenu(MonopolyClient client, Property property, GameScreenController gameScreenController) {
        this.client = client;
        this.property = property;
        this.gameScreenController= gameScreenController;
        this.player = client.getMonopolyGame().getPlayerController().getPlayers().get(property.getOwnerId());

        updateMenu();
    }

    private void updateMenu() {
        int playerBalance = player.getBalance();

        // Mortgage is not yet implemented so it is always disabled
        mortgageButton.setDisable(true);

        // Enable/disable Classroom Button
        if (property instanceof Building) {
            Building building = (Building) property;
            boolean classroomEnabled = (building.getClassroomCount() < 4) &&
                    (building.getLectureHallCount() == 0) &&
                    player.isComplete(building) &&
                    (client.getMonopolyGame().getActivePlayer().getPlayerId() == player.getPlayerId()) &&
                    (building.getClassroomPrice() <= playerBalance);
            addClassroomButton.setDisable(!classroomEnabled);
            addClassroomButton.setText("Add Classroom (" + building.getClassroomPrice() + "$)");

        } else {
            addClassroomButton.setDisable(true);
        }

        // Enable/disable Lecture Hall Button
        if(property instanceof Building) {
            Building building = (Building) property;
            boolean lectureHallEnabled = ( building.getClassroomCount() == 4) &&
                    (building.getLectureHallCount() == 0) &&
                    player.isComplete(building) &&
                    (client.getMonopolyGame().getActivePlayer().getPlayerId() == player.getPlayerId()) &&
                    (building.getLectureHallPrice() <= playerBalance);
            addLectureHallButton.setDisable(!lectureHallEnabled);
            addLectureHallButton.setText("Add Lecture Hall (" + building.getLectureHallPrice() + "$)");
        } else {
            addLectureHallButton.setDisable(true);
        }

        // Edit text
        String textStr = property.getName();

        if (property instanceof Building) {
            Building building = (Building) property;

            boolean isComplete = player.isComplete(building);
            int rent;
            if (building.getClassroomCount() == 0 && !isComplete ) {
                rent = building.getRents().get(0);
            }
            else if (building.getClassroomCount() == 0 && isComplete ) {
                rent = building.getRents().get(0) * 2;
            }
            else if ( building.getLectureHallCount() == 1 ) {
                rent = building.getRents().get(5);
            } else {
                rent = building.getRents().get(building.getClassroomCount());
            }

            textStr += "\n" + building.getColor();
            textStr += "\nClassroom Count: " + building.getClassroomCount();
            textStr += "\nLecture Hall Count: " + building.getLectureHallCount();
            textStr += "\nCurrent Rent: " + rent + "$";
        } else {
            if (property instanceof Dorm)
                textStr += "\nDORM";
            else if (property instanceof Facility)
                textStr += "\nFACILITY";

            textStr += "\nCurrent Rent: " + property.getRents().get(0) + "$";
        }
        textStr += "\nMortgage Price: " + property.getMortgagePrice() + "$";

        text.setText(textStr);
    }

    private boolean showAddClassroomDialog(Building building) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title;
        String content;
        DecimalFormat decimalFormat = new DecimalFormat();

        title = "Add House?";
        content = "Do you wish to build a classroom to " + building.getName() + "?\n" +
                "Price: " + decimalFormat.format(building.getClassroomPrice()) + "$\n" +
                "Rent: " + decimalFormat.format(building.getRents().get(building.getClassroomCount())) + " ==> " +
                decimalFormat.format(building.getRents().get(building.getClassroomCount() + 1));

        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();

        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }

    private boolean showAddHotelDialog(Building building) {
        Dialog<ButtonType> dialog = new Dialog<>();

        String title;
        String content;
        DecimalFormat decimalFormat = new DecimalFormat();

        title = "Add Hotel?";
        content = "Do you wish to build a lecture hall to " + building.getName() + "?\n" +
                "Price: " + decimalFormat.format(building.getLectureHallPrice()) + "$\n" +
                "Rent: " + decimalFormat.format(building.getRents().get(4)) + " ==> " +
                decimalFormat.format(building.getRents().get(5));

        dialog.setTitle(title);
        dialog.setContentText(content);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = dialog.showAndWait();

        return result.isPresent() && (result.get().equals(ButtonType.YES));
    }
}