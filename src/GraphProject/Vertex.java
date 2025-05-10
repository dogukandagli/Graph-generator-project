package GraphProject;


public class Vertex {

        public int x;
        public int y;
        public char name;
        public Vertex() {
            x = 0;
            y = 0;
            name = 0;
        }
        public Vertex(int x, int y, char name) {
            this.x = x;
            this.y = y;
            this.name = name;
        }
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public char getName() {
			return name;
		}
		public void setName(char name) {
			this.name = name;
		}

		
     
}