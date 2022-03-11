package people;

public enum PersonStatus {
    Susceptible, //: person that can be infected (Status S)
    Exposed, //: a person who gets the virus but who is not yet infectious
    Infected, //: person who gets the virus and who can transmit the virus to others (status I)
    Recovered, //: person who is no more infected and no more infectious
}
