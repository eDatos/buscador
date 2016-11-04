package es.gobcan.istac.idxmanager.web.buscador.util;

public class PaginacionUtils {
	
	/**
	 * @param start : Numero del primer resultado, 0..n
	 * @param numFound: Numero de elementos encontrados
	 * @param numResultByPage: Numero de resultados en cada pagina
	 * @param numPageVisible: Numero de paginas maximo visible en el controlador de paginacion
	 * @return new long[] {startPage, currentPage, finalPage}
	 */
	public static long[] obtenerInfoPaginacion(long start, long numFound, long numResultByPage, long numPageVisible) {

        long currentPage = start / numResultByPage; // Pagina actual
        long startPage = 0;
        long finalPage = currentPage;
        
        // Establecemos la pagina inicial de la secuencia de paginas del controlador
        long tamSecuencia = (numPageVisible - 1) / 2; // Le quitamos la pag actual
        for (long i = currentPage - 1 ; i > 0 && i >= currentPage - tamSecuencia; i--) {
        	startPage = i;
        }
        
        // Establemos la pagina final de la secuencia de paginas del controlador
        long numPages = numFound / numResultByPage;
        if (numFound % numResultByPage != 0) {
        	numPages++;
        }
        long numPagesRamaining = numPageVisible - ( 1 + currentPage - startPage);
        for (long i = currentPage + 1; i < numPages && i <= currentPage + numPagesRamaining; i++) {
        	finalPage = i;
        }
        
        // Si nos faltaron por poner paginas por delante de la actual, y hay espacio al principio se lo ponemos.
      	for (long i = startPage - 1; i >= 0 && finalPage - startPage + 1 < numPageVisible; i--) {
      		startPage = i;
        }
		
		return new long[] {startPage, currentPage, finalPage};
	}

}
