package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TripServiceTest {
	@Test
    public void when_logged_user_is_null_then_fail() {
        TripService target = new TestableTripService()
                .WithNullLoggedUser();

        Assertions.assertThrows(
            UserNotLoggedInException.class,
            () -> target.getTripsByUser(new User()));
    }

    @Test
    public void when_given_user_is_null_then_fail() {
        TripService target = new TestableTripService()
                .WithNotNullLoggedUser();

        Assertions.assertThrows(NullPointerException.class,
                () -> target.getTripsByUser(null));
    }

    @Test
    public void when_givenUser_is_alone_then_noTrip() {
        TripService target = new TestableTripService()
                .WithNotNullLoggedUser();

        List<Trip> actual = target.getTripsByUser(new User());

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void when_givenUser_has_one_friend_and_notFriend_with_loggedUser_then_noTrip() {
        TripService target = new TestableTripService()
                .WithNotNullLoggedUser();

        User user = new User();
        user.addFriend(new User());

        List<Trip> actual = target.getTripsByUser(user);

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void when_givenUser_is_friendWith_loggedUser_then_return_givenUserTrip() {
        User loggedUser = new User();

        User givenUser = new User();
        givenUser.addFriend(loggedUser);

        List<Trip> expectedTripList = new ArrayList<Trip>();
        expectedTripList.add(new Trip());

        TripService target = new TestableTripService()
                .WithLoggedUser(loggedUser)
                .WithGivenUserAndTrips(givenUser, expectedTripList);

        List<Trip> actual = target.getTripsByUser(givenUser);

        Assertions.assertEquals(1, actual.size());
    }

    public class TestableTripService extends TripService {
        private User _loggedUser;
        private User _givenUser;
        private List<Trip> _tripList;

        public TestableTripService WithNullLoggedUser() {
            _loggedUser = null;

            return this;
        }

        public TestableTripService WithNotNullLoggedUser() {
            _loggedUser = new User();

            return this;
        }

        public TestableTripService WithLoggedUser(User loggedUser) {
            _loggedUser = loggedUser;

            return this;
        }

        public TestableTripService WithGivenUserAndTrips(
                User givenUser,
                List<Trip> tripList) {
            _givenUser = givenUser;
            _tripList = tripList;

            return this;
        }

        @Override
        protected User getLoggedUser() {
            return _loggedUser;
        }

        @Override
        protected List<Trip> findTripsByUser(User user) {
            // TODO check that the user is the given user
            if(_givenUser == user) {
                return _tripList;
            }

            throw new IllegalArgumentException (
                    "Should set given user and trip list");
        }
    }
}
