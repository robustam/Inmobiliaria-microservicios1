@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepo;

    @Autowired
    private IReservaClient reservaClient; // El cliente Feign que crearás

    public String procesarPago(Pago pago) {
        // 1. Validamos que la reserva exista llamando al otro microservicio
        ReservaDTO reserva = reservaClient.buscarReserva(pago.getIdReserva());

        if (reserva == null) {
            return "Error: La reserva no existe.";
        }

        // 2. Simulación de lógica de negocio
        pago.setEstado("REALIZADO");
        pagoRepo.save(pago);

        return "Pago de $" + pago.getMonto() + " procesado con éxito para la reserva #" + reserva.getId();
    }
}