public class ArithmeticExpressionSolver{

    private int operaciones1= 0, operaciones2= 0, operaciones3= 0, operaciones4= 0, maxNivel= 0, nivel= 0;;
    private int indice= 0, pos1=0, pos2= 0;

public String resolve(String operacion){
    operaciones1= 0;
        //determinar cuántos niveles de parentesis hay
        String[] parenth= operacion.replaceAll("[^\\(\\)]", "").split("");
        for(int i=0; i<parenth.length; i++){
            if(parenth[i].equals("(")){
                nivel++;
                if(nivel>maxNivel){ maxNivel= nivel; }
            }
            if(parenth[i].equals(")")){ nivel--; }
        }

        //determinar cuantos grupos de parentesis hay en cada nivel
        int[] parenthPorNivel= new int[maxNivel];
        for(int i=0; i<maxNivel; i++){ parenthPorNivel[i]= 0; }

        for(int i=0; i<parenth.length; i++){
            if(parenth[i].equals("(")){
                nivel++;
                parenthPorNivel[nivel-1]++;
            }
            if(parenth[i].equals(")")){ nivel--; }
        }

        if(maxNivel>0){
            System.out.println(operacion);
            while(true){
                if(operacion.charAt(operaciones1)=='('){ nivel++; } 
                if(operacion.charAt(operaciones1)==')'){ nivel--; }
                if(nivel==maxNivel && parenthPorNivel[maxNivel-1]>0){
                    parenthPorNivel[maxNivel-1]--;
                    operacion= resolverClase(operacion);
                    System.out.println("operacion: "+operacion);
                    operaciones1--;
                }
                operaciones1++;
                if(parenthPorNivel[maxNivel-1]<=0){ maxNivel--; operaciones1= 0; nivel= 0; System.out.println();}
                if(maxNivel==0){ break; }
            }
        }
        return(resolveOperation(operacion));
    }

    public String resolverClase(String operacion){
        String temp= "", resultado= ""; int posParenth= operaciones1;
        if(operacion.charAt(operaciones1)=='('){
            while(true){
                if(operacion.charAt(operaciones1)=='('){ operacion= deleteChar(operacion, operaciones1); }
                if(operacion.charAt(operaciones1)==')'){ operacion= deleteChar(operacion, operaciones1); operaciones1-=2; nivel--; break; }
                temp+= operacion.charAt(operaciones1);
                operaciones1++;
            }
        }

        for(int i=0; i<operacion.length(); i++){
            if(i==posParenth){ resultado+= resolveOperation(temp); i+= temp.length()-1; }
            else{ resultado+= operacion.charAt(i); }
        }

        operacion= resultado;
        return operacion;
    }

    public String resolveOperation(String cadena){
        operaciones2= 0; operaciones3= 0; operaciones4= 0;
        //determinar numero de operadores
        for(int i=0; i<cadena.length(); i++){
            if(cadena.charAt(i)=='*'||cadena.charAt(i)=='/'){ operaciones3++; }
            if(cadena.charAt(i)=='^'){ operaciones2++; }
        }

        //resolver operaciones individuales
       //System.out.println(cadena);
        cadena= resolverOrden(cadena, operaciones2, '^', '^');
        cadena= resolverOrden(cadena, operaciones3, '*', '/');
        for(int i=0; i<cadena.length(); i++){
            if(cadena.charAt(i)=='+'||cadena.charAt(i)=='-'){ operaciones4++; }
        }
        if(operaciones4== 1 && cadena.charAt(0)=='-'){ operaciones4--; }
        cadena= resolverOrden(cadena, operaciones4, '+', '-');

        return cadena;
    }

    // funciones extra //

    private String resolverOrden(String cadena, int numOperadores, char c1, char c2){
        indice= 0; String resultado= "";
        Float valorA= 0f, valorB= 0f;
        System.out.println(cadena);
        while(true){
            String tempString= "";
            if(cadena.charAt(indice)==c1||cadena.charAt(indice)==c2 && cadena.charAt(0)!='-'){
                valorA= foundValorA(cadena);
                valorB= foundValorB(cadena);
                String str= operar(cadena.charAt(indice), valorA, valorB);

                if(str.charAt(0)!='-' && cadena.charAt(0)!='+'){ str= "+"+str;}
                
                cadena= insertInSpace(cadena, pos1, pos2, str);
                if(cadena.charAt(0)=='+'){
                    for(int i=1; i<cadena.length(); i++){
                        if(cadena.charAt(i)=='+'&& cadena.charAt(i+1)=='+'){ i++;}
                        tempString+= cadena.charAt(i);
                    }
                    cadena= tempString;
                }
                tempString= "";
                for(int i=0; i<cadena.length(); i++){
                    String signos= ""; signos+=cadena.charAt(i);
                    if(i<cadena.length()-1){ signos+= cadena.charAt(i+1); }
                    switch(signos){
                        case "+-": tempString+= "-"; i++; break;
                        case "-+": tempString+= "-"; i++; break;
                        case "--": tempString+= "+"; i++; break;
                        case "++": tempString+= "+"; i++; break;
                        case "/+": tempString+= "/"; i++; break;
                        case "*+": tempString+= "*"; i++; break;
                        case "^+": tempString+= "^"; i++; break;
                        default: tempString+= cadena.charAt(i);
                    }      
                }
                cadena= tempString;

                numOperadores--;
                System.out.println(cadena);
            }
            if(numOperadores==0){ indice= 0; break; }
            if(indice==cadena.length()-1){ break;}
            indice++;
        }
        return cadena;
    }

    private String operar(char operador, Float valorA, Float valorB){
        Float resultado= valorA;
        String regresar= "";
        switch(operador){
            case '*': resultado= valorA*valorB; break;
            case '/': resultado= valorA/valorB; break;
            case '+': resultado= valorA+valorB; break;
            case '-': resultado= valorA-valorB; break;
            case '^': for(int i=0; i<valorB; i++){ resultado*= valorA; break; }
        }
        regresar= Float.toString(resultado);
        if(regresar.charAt(regresar.length()-1)=='0' && regresar.charAt(regresar.length()-2)=='.'){
            String tempString= "";
            for(int i=0; i<regresar.length()-2; i++){ tempString+= regresar.charAt(i); }
            regresar= tempString;
        }

        indice= -1;
        return regresar;
    }

    /*Elimina una seccion determinada y la reemplaza por un texto*/
    private String insertInSpace(String texto, int pos1, int pos2, String text){
        String resultado= "";
        for(int i=0; i<texto.length(); i++){
            if(i==pos1){ resultado+= text; i+= (pos2-pos1); }
            else{ resultado+= texto.charAt(i); }
        }
        return resultado;
    }

    private float foundValorA(String cadena){
        String temp= ""; float resultado= 0;
        int i= indice-1;
        while(true){
            if(cadena.charAt(indice)=='^'){
                if(i<0||cadena.charAt(i)=='-'){ break; }
            }
            if(i<0||cadena.charAt(i)=='*'||cadena.charAt(i)=='/'||cadena.charAt(i)=='+'||cadena.charAt(i)=='^'){ break; }
            if(cadena.charAt(i+1)=='-' && i+1!=indice){ break; }
            temp+= cadena.charAt(i);
            pos1= i;
            i--;
        }
        resultado= Float.parseFloat(invertirCadena(temp));
        return resultado;
    }

    private float foundValorB(String cadena){
        String temp= ""; float resultado= 0;
        int i= indice+1;
        while(true){
            if(i>=cadena.length()||cadena.charAt(i)=='*'||cadena.charAt(i)=='/'||cadena.charAt(i)=='+'){ break; }
            if(cadena.charAt(i)=='-' && cadena.charAt(i-1)!='/' && cadena.charAt(i-1)!='*'){ break; }
            temp+= cadena.charAt(i);
            pos2= i;
            i++;
        }
        resultado= Float.parseFloat(temp);
        return resultado;
    }

    /*-----------------*/
    private static String invertirCadena(String cadena) {
        String cadenaInvertida = "";
        for (int i = cadena.length() - 1; i >= 0; i--) {
            cadenaInvertida += cadena.charAt(i);
        }
        return cadenaInvertida;
    }

    public String deleteChar(String texto, int pos){
        String resultado= "";
        for(int i=0; i<texto.length(); i++){
            if(!(i==pos)){ resultado+= texto.charAt(i); }
        }
        return resultado;
    }
}
