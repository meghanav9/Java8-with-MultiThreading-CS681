@FunctionalInterface
public interface Observer {
	public abstract void update(Observable obs, Object obj);

}
