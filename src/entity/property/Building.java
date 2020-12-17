package entity.property;

import java.util.ArrayList;

public class Building extends Property{
    int classroomPrice;
    int lectureHallPrice;
    String color;
    int classroomCount;
    int lectureHallCount;

    public Building(String name, int id, int price, ArrayList<Integer> rents, int mortgagePrice,
                    int classroomPrice, int lectureHallPrice, String color) {
        super(name, id, price, rents, mortgagePrice);
        this.classroomPrice = classroomPrice;
        this.lectureHallPrice = lectureHallPrice;
        this.color = color;
        classroomCount = 0;
        lectureHallCount = 0;
    }

    public Building() {
        super();
    }

    public void addClassroom() {
        classroomCount++;
    }

    public void addLectureHall() {
        lectureHallCount++;
    }

    public int getClassroomCount() {
        return classroomCount;
    }

    public void setClassroomCount(int classroomCount) {
        this.classroomCount = classroomCount;
    }

    public int getLectureHallCount() {
        return lectureHallCount;
    }

    public void setLectureHallCount(int lectureHallCount) {
        this.lectureHallCount = lectureHallCount;
    }

    public int getClassroomPrice() {
        return classroomPrice;
    }

    public void setClassroomPrice(int classroomPrice) {
        this.classroomPrice = classroomPrice;
    }

    public int getLectureHallPrice() {
        return lectureHallPrice;
    }

    public void setLectureHallPrice(int lectureHallPrice) {
        this.lectureHallPrice = lectureHallPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name + " Price: " + price + " Color: " + color +
                " Classroom price: " + classroomPrice + " Lecture Hall price: " + lectureHallPrice;
    }
}
