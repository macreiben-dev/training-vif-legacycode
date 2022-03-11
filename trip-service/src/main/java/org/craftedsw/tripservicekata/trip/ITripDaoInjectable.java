package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public interface ITripDaoInjectable {
    List<Trip> findTripsByUser(User user);
}
