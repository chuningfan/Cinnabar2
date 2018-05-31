package cinnabar.core.component.redis.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedisLock implements IRedisLock{

	private RedissonClient redissonClient;
	
	@Override
	public void lock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
	}

	@Override
	public void unlock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
	}

	@Override
	public void lock(String lockKey, int timeout) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
	}

	@Override
	public void lock(String lockKey, TimeUnit unit, int timeout) {
		RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
	}

	public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
	
}
