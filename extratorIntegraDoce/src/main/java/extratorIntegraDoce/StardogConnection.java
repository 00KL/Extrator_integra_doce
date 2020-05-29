//Classe que cria a conexão com o banco de triplas stardog
package extratorIntegraDoce;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.complexible.stardog.Stardog;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.rdf4j.StardogRepository;

public class StardogConnection {
	
	private String server;
    private String dataBase;
    private String username = "admin";//Para finalidade de testes, a ideia é exigir o usename e senha para o acesso
    private String password = "admin";
    
    private ConnectionConfiguration connectionConfig;
    private Repository repository;
        
    final Logger log = LoggerFactory.getLogger(Stardog.class);

    //
	public StardogConnection(String server, String dataBase, String username, String password) {
		super();
		this.server = server;
		this.dataBase = dataBase;
		this.username = username;
		this.password = password;
		
		this.connectionConfig = ConnectionConfiguration
				.to(dataBase)
                .server(server)
                .credentials(username, password);
	}

	public StardogConnection(String server, String dataBase) {
		super();
		this.server = server;
		this.dataBase = dataBase;
		
		try {
			this.connectionConfig = ConnectionConfiguration
					.to(dataBase)
	                .server(server)
	                .credentials(this.username, this.password);
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("Erro ao se conectar com o Stardog\n");
		}
		
	}
    
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//Retorna conexao com o repositorio
	//INPUT: nenhum
	//OUTPUT: Uma conexao com o respositorio 
	public RepositoryConnection getConnection() {
		this.repository = new StardogRepository(this.connectionConfig);
		this.repository.initialize();
		try(RepositoryConnection repoConn = repository.getConnection()) {
			return repoConn;
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("Erro ao se conectar com o Stardog\n");
		}
		return null;
	}
	
	//Termina conexão do banco caso exista
	//INPUT: nenhuma
	//OUPUT: nenhum
	public void closeConnection() {
		if(this.repository.isInitialized())
			this.repository.shutDown();
	}
}
