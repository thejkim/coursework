public class EgyptianFractions {
    /**********************************************************
     Method:         main
     Purpose:        compute the method for finding unit fractions
     of a given fraction
     Parameters:     None
     Preconditions:  None
     Postconditions: None
     ***********************************************************/
    public static void main(String args[])
    {
        Fraction frac = new Fraction();	// local fraction object

        // set up fractions: directly insert numbers here (*)
        frac.setNumerator(2);
        frac.setDenominator(15);

        Fraction unitFraction = new Fraction();
        Fraction result = new Fraction();

        int fracNumeratorInt = frac.getNumerator();
        int fracDenominatorInt = frac.getDenominator();

        System.out.println("Given fraction: " + frac);
        System.out.print(frac + " = ");

        // method computations
        while(fracNumeratorInt != 1)
        {
            while(fracDenominatorInt % fracNumeratorInt != 0)
            {
                // not a multiple of numerator
                fracDenominatorInt++;
            }

            // a multiple of numerator
            unitFraction.numerator = fracNumeratorInt;
            unitFraction.denominator = fracDenominatorInt;
            unitFraction = unitFraction.reduce();

            System.out.print(unitFraction + " + ");

            // original fraction - unit fraction
            result = frac.subtract(unitFraction);

            fracNumeratorInt = result.getNumerator();
            fracDenominatorInt = result.getDenominator();
            frac.setNumerator(fracNumeratorInt);
            frac.setDenominator(fracDenominatorInt);

        }
        System.out.print(frac);

    }
}
