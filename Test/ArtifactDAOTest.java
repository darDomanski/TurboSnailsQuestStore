import com.codecool.quest_store.dao.ArtifactDAO;
import com.codecool.quest_store.model.Item;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.codecool.quest_store.dao.DBConnector;

import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


public class ArtifactDAOTest {

    @Mock
    DBConnector connectorMock;

    
    @BeforeEach
    public void init() {
        connectorMock = mock(DBConnector.class);
    }


    @Test
    public void checkIfGetAllThrowsNullPointerExcWhenConnectionWithDataBaseFailed() {
        ArtifactDAO artifactDAO = new ArtifactDAO(connectorMock);
        assertDoesNotThrow(()-> artifactDAO.getAll(), "Connection problem with data base!");
    }


    @Test
    public void checkIfGetAllReturnEmptyListWhenConnectionWithDataBaseFailed() {
        ArtifactDAO artifactDAO = new ArtifactDAO(connectorMock);

        List<Item> listFromMethod = artifactDAO.getAll();

        List<Item> expectedList = new ArrayList<>();
        assertEquals(expectedList, listFromMethod);
    }


    @Test
    public void addMethodTestIfItemtWillBeNull() {

        ArtifactDAO artifactDAO = new ArtifactDAO(connectorMock);

        assertDoesNotThrow(()->artifactDAO.add(null));
    }






}
