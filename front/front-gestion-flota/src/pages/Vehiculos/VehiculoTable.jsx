import Table from "../../components/Table/Table";

function VehiculoTable({ vehiculos, onEdit, onDelete, onAsignar }) {

    return (

        <Table>

            <thead>

                <tr>

                    <th>Patente</th>
                    <th>Marca</th>
                    <th>Nombre</th>
                    <th>Modelo</th>
                    <th>Año</th>
                    <th>Km Actual</th>
                    <th>Conductor Asignado</th>
                    <th>Acciones</th>

                </tr>

            </thead>

            <tbody>

                {vehiculos.map((v) => (

                    <tr key={v.id}>

                        <td>{v.patente}</td>
                        <td>{v.marca}</td>
                        <td>{v.nombre}</td>
                        <td>{v.modelo}</td>
                        <td>{v.anio}</td>
                        <td>{v.kilometrajeActual?.toLocaleString("es-CL")}</td>
                        <td>{v.conductorNombre || "Sin asignar"}</td>

                        <td>

                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(v)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(v.id)}
                                >
                                    Eliminar
                                </button>

                                <button
                                    className="btn-select"
                                    onClick={() => onAsignar(v)}
                                >
                                    Asignar Conductor
                                </button>

                            </div>

                        </td>

                    </tr>

                ))}

            </tbody>

        </Table>

    );

}

export default VehiculoTable;