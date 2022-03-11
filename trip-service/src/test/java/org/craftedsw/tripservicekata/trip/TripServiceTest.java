package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    public class TestableTripService extends TripService {
        private User _loggedUser;

        public TestableTripService WithNullLoggedUser() {
            _loggedUser = null;

            return this;
        }

        public TestableTripService WithNotNullLoggedUser() {
            _loggedUser = new User();

            return this;
        }

        @Override
        protected User getLoggedUser() {
            return _loggedUser;
        }
    }
}
