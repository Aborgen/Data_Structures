public class Room extends MapSite {
    public int roomNumber;
    public Wall[] sides;
    public MapSite[] mapsites;

    public Room() {}
    public Wall getSide(int side) { return new Wall(); }
    public void setSide(int side, Wall wall) {}
}
