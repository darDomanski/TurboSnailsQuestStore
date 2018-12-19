import com.codecool.quest_store.dao.ArtifactDAO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.codecool.quest_store.dao.DBConnector;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class ArtifactDAOTest {

    @Mock
    DBConnector connectorMock = mock(DBConnector.class);

    @Test
    public void checkIfGetAllThrowsNullPointerExcWhenConnectionFailed() {
        ArtifactDAO artifactDAO = new ArtifactDAO(connectorMock);
        assertDoesNotThrow(()-> artifactDAO.getAll(), "Connection problem with data base!");
    }


}
