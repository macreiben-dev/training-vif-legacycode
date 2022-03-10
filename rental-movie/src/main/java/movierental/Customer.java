package movierental;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    public static final int RENTAL_AMOUNT_REGULAR = 2;
    public static final double RENTAL_AMOUNT_CHILDREN = 1.5;
    public static final int THREE_DAYS = 3;
    public static final int ZERO = 0;
    private String _name;
    private List<Rental> _rentals = new ArrayList<>();
    private static final int TWO_DAYS = 2;

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.add(arg);
    }

    public String getName() {
        return _name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;

        // TODO inject this one to constructor ... and then wonder why a customer needs a report builder ? :)
        SimpleReportBuilder builder = new SimpleReportBuilder();

        // HOOK#Simple-report-000-beginning
        String name = getName();

        // TODO result should be generated inside the builder.
        String result = builder.buildReportBeginning(name);

        for (Rental currentRental : _rentals) {
            //determine amounts for currentRental line
            double rentalAmount = computeAmount(currentRental);

            // add frequent renter points
            frequentRenterPoints++;

            // add bonus for a two day new release rental
            if ((currentRental.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                    && currentRental.getDaysRented() > 1)
                frequentRenterPoints++;

            // show figures for this rental
            // HOOK#Simple-report-001-showFiguresForThisRental : show figures for this rental
            String movieTitle = currentRental.getMovie().getTitle();

            result += builder.buildReportAddMovie(result, rentalAmount, movieTitle);

            totalAmount += rentalAmount;
        }

        // add footer lines
        // HOOK#Simple-report-002-addFooter-lines: add footer lines

        result += builder.buildReportAddFooter(totalAmount, frequentRenterPoints);

        return result;
    }

    private double computeAmount(Rental rental) {

        switch (rental.getMovie().getPriceCode()) {

            case Movie.REGULAR:
                return computeStandardRentalAmount(
                        rental,
                        RENTAL_AMOUNT_REGULAR,
                        TWO_DAYS);

            case Movie.CHILDRENS:
                return computeStandardRentalAmount(
                        rental,
                        RENTAL_AMOUNT_CHILDREN,
                        THREE_DAYS);

            case Movie.NEW_RELEASE:
                return rental.getDaysRented() * 3;

            default:
                return ZERO;
        }
    }

    private double computeStandardRentalAmount(
            Rental rental,
            double rentalAmountRegular,
            int rentalDaysCount) {

        double rentalAmount = rentalAmountRegular;

        if (rentedMoreThan(rental, rentalDaysCount))
            rentalAmount += computeExtraRentalAmount(rental, rentalDaysCount);

        return rentalAmount;
    }


    private double computeExtraRentalAmount(Rental rental, int rentalDaysCount) {
        return (rental.getDaysRented() - rentalDaysCount) * 1.5;
    }

    private boolean rentedMoreThan(Rental rental, int rentalDaysCount) {
        return rental.getDaysRented() > rentalDaysCount;
    }


    public class SimpleReportBuilder {

        private String _result;

        private String buildReportAddFooter(double totalAmount, int frequentRenterPoints) {
            String footer = "Amount owed is " + String.valueOf(totalAmount) + "\n";
            footer += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";

            return footer;
        }

        private String buildReportAddMovie(String result, double rentalAmount, String movieTitle) {
            return "\t" + movieTitle + "\t" + String.valueOf(rentalAmount) + "\n";
        }

        private String buildReportBeginning(String name) {
            return "Rental Record for " + name + "\n";
        }
    }

}
