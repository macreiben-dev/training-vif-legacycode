package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();

		User loggedUser = getLoggedUser();

		boolean isFriend = false;

		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}

		for (User friend : user.getFriends()) {
			if (friend.equals(loggedUser)) {
				return findTripsByUser(user);
			}
		}
//		if (isFriend) {
//			tripList = findTripsByUser(user);
//		}
		return tripList;
	}

	protected List<Trip> findTripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}
}
