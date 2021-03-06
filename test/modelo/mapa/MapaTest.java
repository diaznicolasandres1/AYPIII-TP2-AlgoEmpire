package modelo.mapa;

import modelo.excepciones.NoHayLugarSuficenteParaColocarEdificioException;
import modelo.juego.Oro;
import modelo.excepciones.CasilleroOcupadoException;
import modelo.excepciones.PosicionFueraDeRangoException;
import modelo.edificios.castillo.Castillo;
import modelo.edificios.cuartel.Cuartel;
import modelo.edificios.plazacentral.PlazaCentral;
import modelo.excepciones.TamanioInvalidoException;
import modelo.unidades.Colocable;
import modelo.unidades.aldeano.Aldeano;
import modelo.unidades.armadeasedio.ArmaDeAsedio;
import modelo.unidades.arquero.Arquero;
import modelo.unidades.espadachin.Espadachin;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MapaTest {

    private final Oro oro = new Oro(10000);

    @Test(expected = TamanioInvalidoException.class)
    public void test01crearMapaConMedidasNegativasLanzaExcepcion() {
        Mapa mapa = new Mapa(-100, -100);
    }

    @Test(expected = TamanioInvalidoException.class)
    public void test02crearMapaConMedidasNulasLanzaExcepcion() {
        Mapa mapa = new Mapa(0, 0);
    }

    @Test(expected = TamanioInvalidoException.class)
    public void test03crearMapaConMedidasLimitesLanzaExcepcion() {
        Mapa mapa = new Mapa(11, 11);
    }

    @Test
    public void test04crearMapaConMedidasLimitesCreaCorrectamente() {
        Mapa mapa = new Mapa(12, 12);

        Assert.assertNotNull(mapa);
    }

    @Test
    public void test03creaMapaConMedidasGrandes() {
        Mapa mapa = new Mapa(500, 200);

        Assert.assertNotNull(mapa);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test04mapaColocaDosUnidadesEnMismaPosicionLanzaExcepcion() {

        Mapa mapa = new Mapa(50, 25);
        Aldeano unAldeano = new Aldeano(this.oro);
        Aldeano otroAldeano = new Aldeano(this.oro);

        mapa.colocarUnidad(unAldeano, 4, 5);
        mapa.colocarUnidad(otroAldeano, 4, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test05mapaColocaUnidadFueraDeRangoLanzaExcepcion() {

        Mapa mapa = new Mapa(61, 15);
        Aldeano unAldeano = new Aldeano(this.oro);

        mapa.colocarUnidad(unAldeano, 100, 100);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test06mapaColocaUnidadConMapaCompletoLanzaExcepcion() {

        int altura = 20;
        int base = 14;
        int indice = 0;
        Mapa mapa = new Mapa(base, altura);
        ArrayList<Aldeano> aldeanos = new ArrayList<>();
        Aldeano ultimoAldeano = new Aldeano(this.oro);
        for (int i = 0; i < altura * base; i++) {
            aldeanos.add(new Aldeano(this.oro));
        }

        for (int i = 1; i <= altura; i++) {
            for (int j = 1; j <= base; j++) {
                mapa.colocarUnidad(aldeanos.get(indice), i, j);
                indice++;
            }
        }
        mapa.colocarUnidad(ultimoAldeano, 6, 10);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test07plazaSeColocaEnMapaYSeIntentaPonerUnidadEnMismoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);

        mapa.colocarEdificio(plaza, 4, 10, 10);

        mapa.colocarUnidad(aldeano, 10, 10);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test08plazaSeColocaEnMapaYSeIntentaPonerUnidadEnLugarOcupadoPorEdificioLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);

        mapa.colocarEdificio(plaza, 4, 10, 10);

        mapa.colocarUnidad(aldeano, 11, 10);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test09plazaSeColocaEnMapaYSeIntentaPonerUnidadEnLugarOcupadoPorEdificioLanzaExcepcion() {
        Mapa mapa = new Mapa(20, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);

        mapa.colocarEdificio(plaza, 4, 10, 10);

        mapa.colocarUnidad(aldeano, 11, 11);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test10plazaSeColocaEnMapaYSeIntentaPonerUnidadEnLugarOcupadoPorEdificioLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);

        mapa.colocarEdificio(plaza, 4, 10, 10);

        mapa.colocarUnidad(aldeano, 10, 11);
    }

    @Test
    public void test11castilloSeColocaEnMapaYSeIntentaPonerUnidadEnLugarOcupadoPorCastilloDeberiaLanzar16Excepciones() {

        Mapa mapa = new Mapa(20, 20);
        Castillo castillo = new Castillo(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);
        mapa.colocarEdificio(castillo, 16, 10, 10);
        int cantidadDeVecesLanzadaExcepcion = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    mapa.colocarUnidad(aldeano, 10 + i, 10 + j);
                } catch (CasilleroOcupadoException error) {
                    cantidadDeVecesLanzadaExcepcion++;
                }
            }
        }

        Assert.assertEquals(16, cantidadDeVecesLanzadaExcepcion);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test12plazaSeColocaFueraDelRangoPositivoDelMapaLanzaExcepcion() {

        Mapa mapa = new Mapa(30, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);

        mapa.colocarEdificio(plaza, 4, 35, 100);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test13plazaSeColocaFueraDelRangoNegativoDelMapaLanzaExcepcion() {

        Mapa mapa = new Mapa(30, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);

        mapa.colocarEdificio(plaza, 4, -1000, -1000);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test14plazaSeColocaFueraDelRangoNuloDelMapaLanzaExcepcion() {

        Mapa mapa = new Mapa(30, 20);
        PlazaCentral plaza = new PlazaCentral(this.oro);

        mapa.colocarEdificio(plaza, 4, 0, 0);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test15mapaColocaUnidadYSeColocaEdificioEnElMismoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(50, 40);
        PlazaCentral plaza = new PlazaCentral(this.oro);
        Espadachin espadachin = new Espadachin(this.oro);

        mapa.colocarUnidad(espadachin, 23, 22);

        mapa.colocarEdificio(plaza, 4, 23, 22);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test16mapaMueveUnidadHaciaOtroLugarYSeIntentaColocarUnidadEnNuevoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 25);
        Posicion posicion = new Posicion(5, 5);
        Espadachin espadachin = new Espadachin(this.oro);
        Arquero arquero = new Arquero(this.oro);
        espadachin.setPosicion(posicion);

        mapa.colocarUnidad(espadachin, 5, 5);
        mapa.moverUnidadDesdeHasta(espadachin, 5, 5, 6, 6);

        mapa.colocarUnidad(arquero, 6, 6);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test17mapaMueveUnidadFueraDelRangoDelMapaLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 30);
        Posicion posicion = new Posicion(10, 11);
        Espadachin espadachin = new Espadachin(this.oro);
        espadachin.setPosicion(posicion);

        mapa.colocarUnidad(espadachin, 11, 10);

        mapa.moverUnidadDesdeHasta(espadachin, 11, 10, 100, 100);
    }

    @Test(expected = PosicionFueraDeRangoException.class)
    public void test18mapaMueveUnidadFueraDelRangoDeUnidadLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 30);
        Espadachin espadachin = new Espadachin(this.oro);

        mapa.colocarUnidad(espadachin, 9, 8);

        espadachin.moverHacia(new Posicion(22, 22), mapa);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test19mapaMueveUnidadHaciaLugarOcupadoPorEdificioLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 36);
        Posicion posicion = new Posicion(3, 1);
        ArmaDeAsedio armaAsedio = new ArmaDeAsedio(this.oro);
        Cuartel cuartel = new Cuartel(this.oro);
        armaAsedio.setPosicion(posicion);

        cuartel.colocarseEn(mapa, 1, 1);
        mapa.colocarUnidad(armaAsedio, 1, 3);

        mapa.moverUnidadDesdeHasta(armaAsedio, 1, 3, 1, 2);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test20mapaMueveUnidadHaciaLugarOcupadoPorOtraUnidadLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 36);
        Posicion unaPosicion = new Posicion(3, 1);
        Posicion otraPosicion = new Posicion(2, 2);
        ArmaDeAsedio armaAsedio = new ArmaDeAsedio(this.oro);
        Arquero arquero = new Arquero(this.oro);
        armaAsedio.setPosicion(unaPosicion);
        arquero.setPosicion(otraPosicion);

        mapa.colocarUnidad(arquero, 2, 2);
        mapa.colocarUnidad(armaAsedio, 1, 3);

        mapa.moverUnidadDesdeHasta(armaAsedio, 1, 3, 2, 2);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test21mapaColocaYDescolocaUnidadYLuegoColocaUnidadDosVecesEnMismoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 40);
        Arquero arquero = new Arquero(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);

        mapa.colocarUnidad(arquero, 10, 10);
        mapa.descolocarColocable(10, 10);
        mapa.colocarUnidad(aldeano, 10, 10);

        mapa.colocarUnidad(arquero, 10, 10);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test22mapaColocaYDescolocaEdificioYLuegoColocaUnidadDosVecesEnMismoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 40);
        Castillo castillo = new Castillo(this.oro);

        mapa.colocarEdificio(castillo, 16, 10, 10);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mapa.descolocarColocable(10 + i, 10 + j);
            }
        }

        for (int i = 0; i < 8; i++) {
            mapa.colocarUnidad(new Arquero(this.oro), 10 + i, 10);
            mapa.colocarUnidad(new Arquero(this.oro), 10 + i, 11);
        }

        mapa.colocarUnidad(new Aldeano(this.oro), 15, 10);
    }

    @Test
    public void test23mapaBuscaColocablesEnRangosValidosYDevuelveColocablesCorrectos() {

        Mapa mapa = new Mapa(20, 20);
        Castillo castillo = new Castillo(this.oro);
        Aldeano unAldeano = new Aldeano(this.oro);
        Aldeano otroAldeano = new Aldeano(this.oro);

        mapa.colocarEdificio(castillo, 16, 10, 10);
        mapa.colocarUnidad(unAldeano, 14, 14);
        mapa.colocarUnidad(otroAldeano, 9, 9);

        ArrayList<Colocable> colocablesEnRango = mapa.buscarColocablesEnRangoDe(10, 10, 3);

        Assert.assertTrue(colocablesEnRango.contains(unAldeano));
        Assert.assertTrue(colocablesEnRango.contains(otroAldeano));
        Assert.assertTrue(colocablesEnRango.contains(castillo));
    }

    @Test
    public void test24mapaBuscaColocablesEnRangoBordeDelMapaDevuelveColocablesCorrectos() {

        Mapa mapa = new Mapa(20, 20);
        Castillo castillo = new Castillo(this.oro);
        PlazaCentral plaza = new PlazaCentral(this.oro);
        Aldeano unAldeano = new Aldeano(this.oro);
        Aldeano otroAldeano = new Aldeano(this.oro);

        mapa.colocarEdificio(castillo, 16, 1, 1);
        mapa.colocarEdificio(plaza, 4, 5, 4);
        mapa.colocarUnidad(unAldeano, 1, 5);
        mapa.colocarUnidad(otroAldeano, 5, 1);

        ArrayList<Colocable> colocablesEnRango = mapa.buscarColocablesEnRangoDe(1, 1, 3);

        Assert.assertTrue(colocablesEnRango.contains(unAldeano));
        Assert.assertTrue(colocablesEnRango.contains(otroAldeano));
        Assert.assertTrue(colocablesEnRango.contains(castillo));
        Assert.assertTrue(colocablesEnRango.contains(plaza));
    }
    
    @Test
    public void test25mapaColocaAlrededorDePlazaCorrectamente() {

        Mapa mapa = new Mapa(20, 20);
        Aldeano aldeano = new Aldeano(this.oro);
        PlazaCentral plaza = new PlazaCentral(this.oro);

        mapa.colocarEdificio(plaza, 8, 1, 1);
        mapa.colocarAlrededor(1, 1, 8, aldeano);

        Assert.assertEquals(aldeano, mapa.buscarColocableEn(1, 4));
    }
    
    @Test
    public void test26mapaColocaUnidadAlrededorDeCastilloCorrectamente() {

        Mapa mapa = new Mapa(20, 20);
        Aldeano aldeano = new Aldeano(this.oro);
        Castillo castillo = new Castillo(this.oro);

        mapa.colocarEdificio(castillo, 16, 1, 1);
        mapa.colocarAlrededor(1, 1, 16, aldeano);

        Assert.assertEquals(aldeano, mapa.buscarColocableEn(1, 5));
    }

    @Test
    public void test27mapaColocaUnidadEnElUnicoLugarLibreAlrededorDeCastillo() {

        Mapa mapa = new Mapa(20, 20);
        Castillo castillo = new Castillo(this.oro);
        Aldeano aldeano = new Aldeano(this.oro);

        castillo.colocarseEn(mapa, 5, 5);
        for (int i = 0; i < 6; i++) {
            mapa.colocarUnidad(new Aldeano(this.oro), 4, 4 + i);
        }
        for (int i = 1; i < 6; i++) {
            mapa.colocarUnidad(new Aldeano(this.oro), 4 + i, 4);
        }
        for (int i = 0; i < 4; i++) {
            mapa.colocarUnidad(new Aldeano(this.oro), 9, 5 + i);
        }
        for (int i = 0; i < 4; i++) {
            mapa.colocarUnidad(new Aldeano(this.oro), 5 + i, 9);
        }

        mapa.colocarAlrededor(5, 5, 16, aldeano);

        Assert.assertEquals(aldeano, mapa.buscarColocableEn(9, 9));
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test28mapaColocaEdificioParcialmenteSobrePosicionesOcupadasSeLiberanEsasPosicionesYSeColocanUnidadesEnEsasPosicionesLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 25);
        Castillo castillo = new Castillo(this.oro);

        mapa.colocarUnidad(new Aldeano(this.oro), 10, 10);

        try {
            mapa.colocarEdificio(castillo, 16, 7, 7);
        } catch (NoHayLugarSuficenteParaColocarEdificioException e) {
            mapa.colocarUnidad(new Aldeano(this.oro), 8, 8);
        }

        mapa.colocarUnidad(new Aldeano(this.oro), 8, 8);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test29mapaColocaEdificioParcialmenteFueraDelRangoDelMapaSeLiberanEsasPosicionesYSeColocanUnidadesLanzaExcepcion() {

        Mapa mapa = new Mapa(25, 25);
        Castillo castillo = new Castillo(this.oro);

        try {
            mapa.colocarEdificio(castillo, 16, 23, 23);
        } catch (NoHayLugarSuficenteParaColocarEdificioException e) {
            mapa.colocarUnidad(new Aldeano(this.oro), 21, 21);
        }

        mapa.colocarUnidad(new Aldeano(this.oro), 21, 21);
    }
}
