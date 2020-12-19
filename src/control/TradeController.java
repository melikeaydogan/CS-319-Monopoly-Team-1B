package control;

import entity.trade.*;
import java.util.*;

@Deprecated
public class TradeController {
    private ArrayList<TradeOffer> offers;

    public TradeController() {
        offers = new ArrayList<>();
    }

    public TradeController(TradeController tradeController) {
        this.offers = tradeController.offers;
    }

    /**
     * Adds a trade offer to the controller
     * @param senderID ID of the sender player
     * @param receiverID ID of the receiver player
     * @param feeOffered amount of money sender offers
     * @param feeRequested amount of money sender requests
     * @param propertyOffered properties sender offers
     * @param propertyRequested properties sender requests
     */
    public void addOffer(int senderID, int receiverID,
                         int feeOffered, int feeRequested,
                         ArrayList<Integer> propertyOffered,
                         ArrayList<Integer> propertyRequested) {
        // new offer id is last_offer.id + 1
        TradeOffer offer = new TradeOffer(getLastID() + 1, senderID, receiverID,
                feeOffered, feeRequested,
                propertyOffered, propertyRequested,
                OfferStatus.AWAITING_RESPONSE);
        offers.add(offer);
    }

    /**
     * Accepts the trade offer if the offer exists and is awaiting response
     * @param offerID the ID for the trade request to be accepted
     * @return true if and only if the trade is accepted and processed successfully, else returns false
     */
    public boolean acceptOffer(int offerID) {
        TradeOffer offer = getByID(offerID);
        if (offer != null && offer.getStatus() == OfferStatus.AWAITING_RESPONSE) {
            return processOffer(offer);
        }
        return false;
    }

    /**
     * Declines the trade offer if it exists and is awaiting response
     * @param offerID the ID for the trade request to be declined
     * @return true if the trade was set to be declined, else returns false
     */
    public boolean declineOffer(int offerID) {
        TradeOffer offer = getByID(offerID);
        if (offer != null && offer.getStatus() == OfferStatus.AWAITING_RESPONSE) {
            offer.setStatus(OfferStatus.DECLINED);
            return true;
        }
        return false;
    }

    /**
     * Creates a counter offer for a trade offer if it exists and is awaiting response
     * @param offerID ID of the offer to be countered
     * @param feeOffered fee old receiver offers
     * @param feeRequested fee old receiver requests
     * @param propertyOffered properties old receiver offers
     * @param propertyRequested properties old receiver requests
     * @return true if the current trade offer exists, false if DNE
     */
    public boolean counterOffer(int offerID, int feeOffered,
                             int feeRequested,
                             ArrayList<Integer> propertyOffered,
                             ArrayList<Integer> propertyRequested) {
        TradeOffer curOffer = getByID(offerID);
        if (curOffer != null && curOffer.getStatus() == OfferStatus.AWAITING_RESPONSE) {
            TradeOffer counterOffer = curOffer.createCtrOffer(
                    getLastID() + 1,
                    feeOffered, feeRequested,
                    propertyOffered, propertyRequested);
            offers.add(counterOffer);
            return true;
        }
        return false;
    }

    /**
     * Tries to process the trade offer. If any of the exchange is not feasible
     * , i.e. money is insufficient, player built classroom in one property,
     * player doesn't own the property anymore etc., the process doesn't happen
     * with TradeException??? (Do we actually need to implement an exception class?)
     * @param offer the ID of the offer to be processed
     */
    private boolean processOffer(TradeOffer offer) {
        //TODO Write the code that processes the trade
        // behind the scenes. No need for ui nor network involvement?
        offer.setStatus(OfferStatus.ACCEPTED);
        return false;
    }

    /**
     * @param offerID ID of the trade offer searched
     * @return the instance of the trade offer with the given ID returns null if DNE
     */
    private TradeOffer getByID(int offerID) {
        for (TradeOffer offer : offers) {
            if (offer.getOfferID() == offerID)
                return offer;
        }
        return null;
    }

    /**
     * @return the id of the most recent offer, returns 0 if the controller is empty
     */
    private int getLastID() {
        if (offers.size() > 0)
            return offers.get(offers.size() - 1).getOfferID();
        else
            return 0;
    }

    /**
     * @return the list of offers
     */
    public ArrayList<TradeOffer> getOffers() {
        return offers;
    }
}
