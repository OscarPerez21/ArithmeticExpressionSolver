public class ArithmeticExpressionSolver {
    public String resultado= "";
    public int numOperadores= 0, indice= 0;

    public String resolve(String calculo) {
        resultado= "";
        int numClases= 0;
        resultado= calculo;

        if(operationValidate(calculo)){
            String[] clases= calculo.split("([\\+\\-\\*\\/])");
            numClases= clases.length;}
        else{
            resultado= "Error";}

        String[] clases = resultado.split("([\\+\\-\\*\\/])");
        String[] operadores= resultado.replaceAll("[^\\+\\-\\*\\/]", "").split("");
        Float[] valClases= new Float[numClases];
        numOperadores= operadores.length;
        int operadoresTotales = operadores.length; 

        int operaciones3= 0, operaciones4= 0;

        for(int i=0; i<operadores.length; i++){
            if(operadores[i].equals("*")||operadores[i].equals("/")){ operaciones3++; }
            if(operadores[i].equals("+")||operadores[i].equals("-")){ operaciones4++; }
        }

        for(int i=0; i<numClases; i++){ valClases[i]= Float.parseFloat(clases[i]);}

        if(!resultado.equals("Error")){
            int numImpresiones= 0;
            String[] listaOperadores= {"*","/","+","-"};
            int posOperador= 0;
            imprimir(valClases, operadores);

            while(numOperadores>0){
                if(operadores[indice].equals("n")){ break; }
                if(operadores[indice].equals(listaOperadores[posOperador]) || operadores[indice].equals(listaOperadores[posOperador+1])){ 
                    if(posOperador==0){ operaciones3--; }
                    if(posOperador==2){ operaciones4--; }  

                    operaciones(indice, operadores, valClases, operadores[indice]);
                    indice= -1;
                }
                if(operaciones3==0){ posOperador= 2; }
                indice++;
            }

            System.out.println(" "+valClases[0]);
            if(resultado.length()!=1){
                String tempResult= invertirCadena(resultado); resultado= "";
                if(tempResult.charAt(1)=='.' && tempResult.charAt(0)=='0'){
                    for (int i= 0; i<tempResult.length()-2; i++){ resultado+= tempResult.charAt(i+2); }
                }
                else{ resultado= tempResult; }
                if(resultado.equals("ytinifnI")){ resultado= "División entre 0"; }
                else{ resultado= invertirCadena(resultado); }
            }
        }
        return resultado;
    }

    //Resolver orden de operaciones

    private void operaciones(int i, String[] operadores, Float[] valClases, String operador){
        switch(operador){
            case "*": valClases[i]= valClases[i]*valClases[i+1]; break;
            case "/": valClases[i]= valClases[i]/valClases[i+1]; break;
            case "+": valClases[i]= valClases[i]+valClases[i+1]; break;
            case "-": valClases[i]= valClases[i]-valClases[i+1]; break;
        }
        operadores[i]= "n"; numOperadores--;
        valClases= bubbleSortLastFloat((i+1), valClases, valClases[i+1]);
        operadores= bubbleSortLastString(operadores, "n");
        resultado= Float.toString(valClases[i]);
        imprimir(valClases, operadores);
    }

    private void imprimir(Float[] str, String[] op){
        for(int i=0; i<numOperadores; i++){
            System.out.print(" "+str[i]+" "+op[i]);
            if(i==numOperadores-1){
                System.out.print(" "+str[i+1]+" ");
            }
        }
        if(numOperadores!=0){ System.out.println(); }
    }

    //validar operación
    private Boolean operationValidate(String str){
        Boolean result= true;
        for(int i=0; i<str.length(); i++){
            if(i!=0){ 
                if(isOperator(str.charAt(i))&&isOperator(str.charAt(i-1))){
                    System.out.println("Error: no pueden haber dos operadores seguidos");
                    result= false;}
            }
        }
        if(isOperator(str.charAt(0))){
            resultado= invertirCadena(resultado);
            resultado+= "0";
            resultado= invertirCadena(resultado);
        }

        if(isOperator(str.charAt(str.length()-1))){
            String tempStr= "";
            for(int i=0; i< str.length()-1; i++){ tempStr+= str.charAt(i); }
            resultado= tempStr;
        }

        return result;
    }

    private Boolean isOperator(Character list){
        Character[] onList= {'+','-','*','/'};
        Boolean result= false;
        for(Character caracter : onList){
            if(list.equals(caracter)){ result= true; }
        }
        return result;
    }

    //funciones extra / locales
    private static String invertirCadena(String cadena) {
        String cadenaInvertida = "";
        for (int i = cadena.length() - 1; i >= 0; i--) {
            cadenaInvertida += cadena.charAt(i);
        }
        return cadenaInvertida;
    }

    private Float[] bubbleSortLastFloat(int indice, Float[] str, Float VALOR) {
        int n = str.length;
        int lastUsedIndex = 0;
        int[] pos= new int[n];

        for(int i=0; i<n-1; i++){ pos[i]= i; }
        for (int i = 0; i < n - 1; i++) {
            if (str[i].equals(VALOR) && pos[i]==indice) {
                Float temp = str[i];
                str[i] = str[i + 1];
                str[i + 1] = temp;

                int temp2= pos[i];
                pos[i]= pos[i+1];
                pos[i+1] = temp2;
            }
        }
        return str;
    }

    private String[] bubbleSortLastString(String[] str, String VALOR){
        int n = str.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (str[j].equals(VALOR)) {
                    String temp = str[j];
                    str[j] = str[j + 1];
                    str[j + 1] = temp;
                }
            }
        }
        return str;
    }

}
