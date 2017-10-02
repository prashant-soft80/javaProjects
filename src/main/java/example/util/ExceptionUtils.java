package example.util;

import java.util.function.Consumer;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class ExceptionUtils {

    public static void checkedToRuntimeException(RunnableWithThrowable runnableWithThrowable) {
        try {
            runnableWithThrowable.run();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static <T> T checkedToRuntimeException(ComputableWithThrowable<T> runnableWithThrowable) {
        try {
            return runnableWithThrowable.compute();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static <T> T swallowException(ComputableWithThrowable<T> computableWithThrowable, T returnOnException) {
        try {
            return computableWithThrowable.compute();
        } catch (Throwable ignored) {
            return returnOnException;
        }
    }

    public static void swallowException(RunnableWithThrowable runnableWithThrowable) {
        try {
            runnableWithThrowable.run();
        } catch (Throwable ignored) {
            // swallow
        }
    }

    public static final int MAX_CAUSE_SEARCH = 20; // depth to search for exception

    public static boolean containsException(Throwable t, Class<?> clazz) {
        return getException(t, clazz) != null;
    }

    public static <T> T getException(Throwable t, Class<T> clazz) {
        return getException(t, clazz, MAX_CAUSE_SEARCH);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getException(Throwable t, Class<T> clazz, int maxLevel) {
        for (int level = 0; level < maxLevel; level++) {
            if (t == null) {
                return null;
            }
            if (clazz.isAssignableFrom(t.getClass())) {
                return (T) t;
            }
            t = t.getCause();
        }
        return null;
    }

    /**
     * Can't test this one..
     */
    public static void exitOnUncaughtException() {
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                System.exit(-1);
            }
        });
    }


    public static <T, E extends Throwable> Consumer<T> throwableConsumer(ConsumerWithThrowable<T, E> consumer) throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Throwable exception) {
                sneakyThrow(exception);
            }
        };
    }

    //based on http://www.mail-archive.com/javaposse@googlegroups.com/msg05984.html
    private static <E extends Throwable> void sneakyThrow(Throwable t) throws E {
        if (t != null) {
            //noinspection unchecked
            throw (E) t;
        }
    }

    public static Throwable getRootCause(Throwable t, int maxLevel) {
        for (int level = 0; level < maxLevel; level++) {
            if (t.getCause() == null) {
                return t;
            }
            t = t.getCause();
        }
        return t;
    }
}
