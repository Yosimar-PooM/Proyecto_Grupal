package Agenetico;

public class Individuo {
    private int _Largo = 11;
    private byte[] _Genes = new byte [_Largo];
    private double _Soporte = 0;
    private int _Index;
    private String[] _ItemS;
    //private String _ItemS;
    //private int _ItemN;
    public Individuo(){
        for (int i = 0; i < _Largo; i++)
        {
            byte Gen = (byte) Math.round(Math.random());
            _Genes[i] = Gen;
        }
    }

    public int getLargo() {
        return _Largo;
    }

    public void setLargo(int _Largo) {
        this._Largo = _Largo;
    }

    public byte getGenes(int index) {
        return _Genes[index];
    }

    public void setGenes(byte[] _Genes) {
        this._Genes = _Genes;
    }

    public void setSingleGene(int index, byte Gen) {
        this._Genes[index] = Gen;
    }

    public int getIndex() {
        return _Index;
    }

    public void setIndex(int _Index) {
        this._Index = _Index;
    }

    public double getSoporte() {
        return _Soporte;
    }

    public void setSoporte(double _Soporte) {
        this._Soporte = _Soporte;
    }

    public byte[] getGenes() {
        return _Genes;
    }
    public void setSingleItemS(int index, String item){
        this._ItemS[index] = item;
    }

    public String getItemS(int index) {
        return _ItemS[index];
    }
    public String[] getItemS(){
        return _ItemS;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < _Genes.length; i++) {
            geneString += getGenes(i);
        }
        return geneString;
    }
    public void Inicializar_ItemV(int dimension){
        _ItemS = new String[dimension];
    }

}
