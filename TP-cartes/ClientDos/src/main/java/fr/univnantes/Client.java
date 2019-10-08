package fr.univnantes;

import java.rmi.Naming;

class Client {
	public Client() {
		
	}

	public static void main(String[] args) {
		try {
			IDos stub = (IDos)Naming.lookup("rmi://localhost:1099/Hello");
			
			String response = stub.parler("Grenouille");
			System.out.println("Réponse du serveur: " + response);
		} catch(Exception e) {
			System.err.println("Erreur " + e.toString());
			e.printStackTrace();
		}
	}
}