package cinnabar.core.component.redis.lock;

import java.util.concurrent.TimeUnit;

public interface IRedisLock {
	
	void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);
    
    void lock(String lockKey, TimeUnit unit ,int timeout);
	
}
