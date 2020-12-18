package entity.trade;

import entity.property.Property;

import java.util.*;

public class TradeOffer {
    private int offerID;
    private int senderID;
    private int receiverID;
    private int feeOffered;
    private int feeRequested;
    /**
     * Property ID's for the properties that sender player offers
     */
    private ArrayList<Integer> propertyOffered;
    /**
     * Property ID's for the properties that sender player requests
     */
    private ArrayList<Integer> propertyRequested;
    private OfferStatus status;

    public TradeOffer(int offerID,
                      int senderID,
                      int receiverID,
                      int feeOffered,
                      int feeRequested,
                      ArrayList<Integer> propertyOffered,
                      ArrayList<Integer> propertyRequested,
                      OfferStatus status) {
        this.offerID = offerID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.feeOffered = feeOffered;
        this.feeRequested = feeRequested;
        this.propertyOffered = new ArrayList<>(propertyOffered);
        this.propertyRequested = new ArrayList<>(propertyRequested);
        this.status = status;
    }

    public TradeOffer(TradeOffer offer) {
        this(offer.offerID,
                offer.senderID,
                offer.receiverID,
                offer.feeOffered,
                offer.feeRequested,
                offer.propertyOffered,
                offer.propertyRequested,
                offer.status);
    }

    public TradeOffer() {

    }

    /**
     * Sets the status of this trade to COUNTER_OFFERED
     * and creates a counter offer for this offer
     * @param offerID the new offer ID for the counter offer
     * @param feeOffered the new money offered by the receiver player
     * @param feeRequested the new money requested by the receiver player
     * @param propertyOffered the new properties offered by the receiver player
     * @param propertyRequested the new properties requested by the receiver player
     * @return the instance of the counter offer
     */
    public TradeOffer createCtrOffer(int offerID, int feeOffered, int feeRequested,
                                     ArrayList<Integer> propertyOffered,
                                     ArrayList<Integer> propertyRequested) {
        // Create the counter offer, receiver becomes the sender
        // also the newly created counter offer is awaiting response
        TradeOffer counterOffer = new TradeOffer(offerID,
                receiverID, senderID,
                feeOffered, feeRequested,
                propertyOffered, propertyRequested,
                OfferStatus.AWAITING_RESPONSE);
        status = OfferStatus.COUNTER_OFFERED;
        return counterOffer;
    }

    public int getOfferID() {
        return offerID;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public int getFeeOffered() {
        return feeOffered;
    }

    public int getFeeRequested() {
        return feeRequested;
    }

    public ArrayList<Integer> getPropertyOffered() {
        return propertyOffered;
    }

    public ArrayList<Integer> getPropertyRequested() {
        return propertyRequested;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}

