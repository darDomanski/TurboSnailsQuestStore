import com.codecool.quest_store.dao.ArtifactDAO;
import com.codecool.quest_store.dao.DBConnector;
import com.codecool.quest_store.dao.QSUserDAO;
import com.codecool.quest_store.model.QSUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class QSUserTest {

    @Mock
    DBConnector connectorMock;


    @BeforeEach
    public void init() {
        connectorMock = mock(DBConnector.class);
    }

    @Test
    public void insertMethodTestIfPersonWillBeNull() {

        QSUserDAO dao = new QSUserDAO(connectorMock);

        assertDoesNotThrow(()->dao.insert(null));
    }
}
