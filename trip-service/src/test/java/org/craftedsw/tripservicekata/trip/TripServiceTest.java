package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TripServiceTest {
	@Test
    public void when_logged_user_is_null_then_fail() {
        TripService target = new TestableTripServiceWithNullLoggedUser();

        Assertions.assertThrows(
            UserNotLoggedInException.class,
            () -> target.getTripsByUser(new User()));
    }

    @Test
    public void when_given_user_is_null_then_fail() {
        TripService target = new TestableTripServiceWithNotNullLoggedUser();

        Assertions.assertThrows(NullPointerException.class,
                () -> target.getTripsByUser(null));
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

    public class TestableTripServiceWithNotNullLoggedUser extends TripService {
        @Override
        protected User getLoggedUser() {
            return new User();
        }
    }

    public class TestableTripServiceWithNullLoggedUser extends TripService {
        @Override
        protected User getLoggedUser() {
            return null;
        }
    }
}
