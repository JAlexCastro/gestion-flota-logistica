import Table from "../../components/Table/Table";
import { useNavigate } from "react-router-dom";

function MantencionTable({ mantenciones, onEdit, onDelete }) {

    const navigate = useNavigate();

    return (
        
        <Table>

            <thead>
                <tr>
                    <th>Vehículo</th>
                    <th>Fecha Mantt</th>
                    <th>Km Actual</th>
                    <th>Prox Mantt</th>
                    <th>km Faltantes</th>
                    <th>Tipo Mantención</th>
                    <th>Estado</th>
                    <th>Taller</th>
                    <th>Observación</th>
                    <th>Acciones</th>

                </tr>
            </thead>

            <tbody>
                {mantenciones.map((m) => (
                    
                    <tr key={m.id}>
                        <td>{m.patente}</td>
                        <td>{m.fecha}</td>
                        <th>{m.kilometrajeActual?.toLocaleString("es-CL")}</th>
                        <td>{m.kilometraje?.toLocaleString("es-CL")}</td>
                        <td
                            style={{
                                color:
                                    m.kilometrajeRestantes <= 0
                                        ? "#dc2626"
                                        : m.kilometrajeRestantes <= 1000
                                        ? "#ea580c"
                                        : m.kilometrajeRestantes <= 5000
                                        ? "#ca8a04"
                                        : "#16a34a",
                                fontWeight: "bold"
                            }}
                        >
                            {m.kilometrajeRestantes.toLocaleString("es-CL")} km
                        </td>
                        <td>{m.tipo}</td>
                        <th>{m.estado}</th>
                        <td>{m.taller}</td>
                        <th>{m.descripcion}</th>

                        <td>

                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={() => navigate(`/vehiculos`)}>
                                    Ver
                                </button>
                                
                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(m)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(m.id)}
                                >
                                    Eliminar
                                </button>


                            </div>

                        </td>

                    </tr>

                ))}

            </tbody>

        </Table>

    );

}

export default MantencionTable;