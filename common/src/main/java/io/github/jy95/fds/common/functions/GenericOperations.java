package io.github.jy95.fds.common.functions;

import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Utility class for generic operations.
 *
 * @author jy95
 * @since 2.2.0
 */
public final class GenericOperations {

    /**
     * No constructor for this class
     */
    private GenericOperations() {
    }

    /**
     * Selects and returns a value from one of two suppliers based on a boolean
     * condition.
     * <p>
     * This method is the equivalent of the ternary operator
     * ({@code condition ? valueIfTrue : valueIfFalse})
     * but uses {@link java.util.function.Supplier Suppliers} to ensure **lazy
     * evaluation**.
     * Only the {@code Supplier} corresponding to the current state of the condition
     * will
     * have its {@code get()} method called, avoiding potentially costly operations
     * if their result is not needed.
     * </p>
     *
     * @param <R>             The common return type of both suppliers and the
     *                        method.
     * @param condition       The boolean expression to evaluate.
     * @param supplierIfTrue  The supplier whose {@code get()} method is called and
     *                        returned if the condition is {@code true}.
     * @param supplierIfFalse The supplier whose {@code get()} method is called and
     *                        returned if the condition is {@code false}.
     * @return The value provided by the selected supplier.
     * @since 2.2.0
     */
    public static <R> R conditionalSelect(
            boolean condition,
            Supplier<R> supplierIfTrue,
            Supplier<R> supplierIfFalse) {

        if (condition) {
            return supplierIfTrue.get();
        }

        return supplierIfFalse.get();
    }

    /**
     * Performs a lazy logical AND (AllMatch) operation across the provided boolean
     * expressions.
     *
     * <p>
     * It returns {@code true} only if all boolean expressions supplied return
     * {@code true}.
     * This method guarantees **short-circuit evaluation**: it stops and returns
     * {@code false}
     * immediately upon finding the first supplier that returns {@code false}.
     * </p>
     *
     * @param suppliers The BooleanSuppliers containing the boolean expressions to
     *                  evaluate.
     * @return {@code true} if all suppliers return true, {@code false} otherwise.
     */
    public static boolean allMatchLazy(BooleanSupplier... suppliers) {
        return Arrays.stream(suppliers)
                .allMatch(BooleanSupplier::getAsBoolean);
    }

    /**
     * Performs a lazy logical OR (AnyMatch) operation across the provided boolean
     * expressions.
     *
     * <p>
     * It returns {@code true} if at least one boolean expression supplied returns
     * {@code true}.
     * This method guarantees **short-circuit evaluation**: it stops and returns
     * {@code true}
     * immediately upon finding the first supplier that returns {@code true}.
     * </p>
     *
     * @param suppliers The BooleanSuppliers containing the boolean expressions to
     *                  evaluate.
     * @return {@code true} if at least one supplier returns true, {@code false}
     *         otherwise.
     */
    public static boolean anyMatchLazy(BooleanSupplier... suppliers) {
        // anyMatch est l'opération de Stream idéale pour l'équivalent du OR logique
        return Arrays.stream(suppliers)
                .anyMatch(BooleanSupplier::getAsBoolean);
    }

}
