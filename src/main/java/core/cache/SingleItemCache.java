package core.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author prash
 * @since 2017-Oct-02
 */
public class SingleItemCache<V> {    protected final AtomicReference<RunnableFuture<V>> taskRef;
    protected final AtomicLong loadTime = new AtomicLong();
    protected final Callable<V> callable;

    public SingleItemCache(final Callable<V> callable) {
        this.callable = callable;
        taskRef = new AtomicReference<>(new FutureTask<>(this.callable));
    }

    public V get() {
        try {
            RunnableFuture<V> vFutureTask = taskRef.get();
            V v;
            if (!vFutureTask.isDone()) {
                vFutureTask.run();
                loadTime.set(System.currentTimeMillis());
                v = vFutureTask.get();
                taskRef.set(new PreCalculatedFuture<V>(v));
            } else {
                v = vFutureTask.get();
            }

            return v;
        } catch (Exception e) {
            taskRef.set(new FutureTask<>(callable));
            throw new RuntimeException("Compute exception.", e);
        }
    }

    public boolean isLoaded() {
        return taskRef.get().isDone();
    }

    public void clear() {
        taskRef.set(new FutureTask<>(callable));
    }

    public long getLoadTime() {
        return loadTime.get();
    }
}
