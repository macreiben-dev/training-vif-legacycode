package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {
    private final ITripDaoInjectable _tripDao;
    // TODO ? Do I have an IoC that works ?
    // TODO How many reference do I have on the default constructor ?

    public TripService() {
        this(new TripDaoInjectable());
    }

    public TripService(ITripDaoInjectable tripDao) {
        _tripDao = tripDao;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        User loggedUser = getLoggedUser();

        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }

        for (User friend : user.getFriends()) {
            if (friend.equals(loggedUser)) {
                return _tripDao.findTripsByUser(user);
            }
        }

        return empty();
    }

    private ArrayList<Trip> empty() {
        return new ArrayList<Trip>();
    }

    protected List<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }
}
