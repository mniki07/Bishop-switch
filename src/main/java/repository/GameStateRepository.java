package repository;

public class GameStateRepository extends GsonRepository<GameState> {
    /**
     * The constructor of the GameStateRepository class.
     */
    public GameStateRepository(){
        super(GameState.class);
    }
}
