package javax.money.spi;

import java.util.*;
import java.util.logging.Logger;

/**
 * This singleton provides access to the services available in the current runtime environment and context. The
 * behaviour can be adapted, by calling {@link Bootstrap#init(ServiceProvider)} before accessing any monetary
 * services.
 *
 * @author Anatole Tresch
 */
public final class Bootstrap {
    /**
     * The ServiceProvider used.
     */
    private static volatile ServiceProvider serviceProviderDelegate;
    /**
     * The shared lock instance user.
     */
    private static final Object LOCK = new Object();

    /**
     * Private singletons constructor.
     */
    private Bootstrap() {
    }

    /**
     * Load the {@link ServiceProvider} to be used.
     *
     * @return {@link ServiceProvider} to be used for loading the services.
     */
    private static ServiceProvider loadDefaultServiceProvider() {
        return new CtDefaultServiceProvider();
    }

    /**
     * Replace the current {@link ServiceProvider} in use.
     *
     * @param serviceProvider the new {@link ServiceProvider}
     * @return the removed , or null.
     */
    public static ServiceProvider init(ServiceProvider serviceProvider) {
        Objects.requireNonNull(serviceProvider);
        synchronized (LOCK) {
            if (Objects.isNull(Bootstrap.serviceProviderDelegate)) {
                Bootstrap.serviceProviderDelegate = serviceProvider;
                Logger.getLogger(Bootstrap.class.getName())
                        .info("Money Bootstrap: new ServiceProvider set: " + serviceProvider.getClass().getName());
                return null;
            } else {
                ServiceProvider prevProvider = Bootstrap.serviceProviderDelegate;
                Bootstrap.serviceProviderDelegate = serviceProvider;
                Logger.getLogger(Bootstrap.class.getName())
                        .warning("Money Bootstrap: ServiceProvider replaced: " + serviceProvider.getClass().getName());
                return prevProvider;
            }
        }
    }

    /**
     * Ge {@link ServiceProvider}. If necessary the {@link ServiceProvider} will be lazily loaded.
     *
     * @return the {@link ServiceProvider} used.
     */
    static ServiceProvider getServiceProvider() {
        if (Objects.isNull(serviceProviderDelegate)) {
            synchronized (LOCK) {
                if (Objects.isNull(serviceProviderDelegate)) {
                    serviceProviderDelegate = loadDefaultServiceProvider();
                }
            }
        }
        return serviceProviderDelegate;
    }

    /**
     * Delegate method for {@link ServiceProvider#getServices(Class)}.
     *
     * @param serviceType the service type.
     * @return the services found.
     * @see ServiceProvider#getServices(Class)
     */
    public static <T> Collection<T> getServices(Class<T> serviceType) {
        return getServiceProvider().getServices(serviceType);
    }

    /**
     * Delegate method for {@link ServiceProvider#getServices(Class)}.
     *
     * @param serviceType the service type.
     * @return the service found, or {@code null}.
     * @see ServiceProvider#getServices(Class)
     */
    public static <T> T getService(Class<T> serviceType) {
        List<T> services = getServiceProvider().getServices(serviceType);
        return services
                .stream()
                .findFirst()
                .orElse(null);
    }

}
