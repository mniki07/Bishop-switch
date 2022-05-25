package repository;

public class GameStateRepository extends GsonRepository<GameState> {
    public GameStateRepository(){
        super(GameState.class);
    }
}
