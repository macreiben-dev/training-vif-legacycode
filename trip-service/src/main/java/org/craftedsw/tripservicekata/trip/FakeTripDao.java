package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class FakeTripDao implements ITripDaoInjectable {
    @Override
    public List<Trip> findTripsByUser(User user) {
        return null;
    }


}
