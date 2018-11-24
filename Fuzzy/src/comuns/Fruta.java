package comuns;

public class Fruta {
	private char cores[];
	private char energia;

	public Fruta(char[] cores, char energia) {
		this.cores = cores;
		this.energia = energia;
	}
	
	
	public char[] getCores() {
		return this.cores;
	}
	
	public char getEnergia() {
		return this.energia;
	}
	
	public void print() {
		System.out.print(cores);
		System.out.println(energia);
	}
	
        public int id3Fruta() {
        switch (this.cores[1]) {
            case 'R': //c1 = R
                switch (this.cores[3]) {
                    case 'R': // c1 = R, c3 = R
                        switch (this.cores[2]) {
                            case 'R': // c1 = R, c2 = R, c3 = R 
                                return 4;
                            case 'G': // c1 = R, c2 = G, c3 = R
                                return 2;
                            case 'B': // c1 = R, c2 = B, c3 = R
                                return 2;
                        }
                    case 'G':
                        switch (this.cores[2]) {
                            case 'R': // c1 = R, c2 = R, c3 = G 
                                return 2;
                            case 'G': // c1 = R, c2 = G, c3 = G
                                return 2;
                            case 'B': // c1 = R, c2 = B, c3 = G
                                return 0;
                        }
                    case 'B':
                        switch (this.cores[2]) {
                            case 'R': // c1 = R, c2 = R, c3 = B 
                                return 2;
                            case 'G': // c1 = R, c2 = G, c3 = B
                                return 4;
                            case 'B': // c1 = R, c2 = B, c3 = B
                                return 2;
                        }
                }
            case 'G': // c1 = G
                if (this.cores[0] == 'W') {
                    return 0;
                }
                if (this.cores[0] == 'K') // c0 = K, c1 = G
                {
                    switch (this.cores[3]) {
                        case 'R': // c0 = K, c1 = G, c3 = R 
                            switch (this.cores[2]) {
                                case 'R': // c0 = K, c1 = G, c2 = R, c3 = R
                                    return 2;
                                case 'G': // c0 = K, c1 = G, c2 = G, c3 = R
                                    return 2;
                                case 'B': // c0 = K, c1 = G, c2 = B, c3 = R
                                    return 0;
                            }
                        case 'G': // c0 = K, c1 = G, c3 = G 
                            switch (this.cores[2]) {
                                case 'R': // c0 = K, c1 = G, c2 = R, c3 = G
                                    return 2;
                                case 'G': // c0 = K, c1 = G, c2 = G, c3 = G
                                    return 4;
                                case 'B': // c0 = K, c1 = G, c2 = B, c3 = G
                                    return 2;
                            }
                        case 'B': // c0 = K, c1 = G, c3 = B 
                            switch (this.cores[2]) {
                                case 'R': // c0 = K, c1 = G, c2 = R, c3 = B
                                    return 0;
                                case 'G': // c0 = K, c1 = G, c2 = G, c3 = B
                                    return 2;
                                case 'B': // c0 = K, c1 = G, c2 = B, c3 = B
                                    return 2;
                            }
                    }
                }
            case 'B': // c1 = B
                switch (this.cores[2]) {
                    case 'R': // c1 = B, c2 = R
                        switch (this.cores[3]) {
                            case 'R': // c1 = B, c2 = R, c3 = R
                                return 2;
                            case 'G': // c1 = B, c2 = R, c3 = G
                                return 0;
                            case 'B': // c1 = B, c2 = R, c3 = B
                                return 2;
                        }
                    case 'G': // c1 = B, c2 = G
                        switch (this.cores[3]) {
                            case 'R': // c1 = B, c2 = G, c3 = R
                                return 0;
                            case 'G': // c1 = B, c2 = G, c3 = G
                                return 2;
                            case 'B': // c1 = B, c2 = G, c3 = B
                                return 2;
                        }
                    case 'B': // c1 = B, c2 = B
                        switch (this.cores[3]) {
                            case 'R': // c1 = B, c2 = B, c3 = R
                                return 2;
                            case 'G': // c1 = B, c2 = B, c3 = G
                                return 2;
                            case 'B': // c1 = B, c2 = B, c3 = B
                                return 4;
                        }
                }
        }
        return 0;
    }
  }
