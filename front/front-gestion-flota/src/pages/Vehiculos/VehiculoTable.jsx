import Table from "../../components/Table/Table";
import RoleGuard from "../../components/Auth/RoleGuard";

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
                    <RoleGuard roles={["ADMIN", "OPERADOR"]}>
                        <th>Acciones</th>
                    </RoleGuard>

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

                                <RoleGuard roles={["ADMIN", "OPERADOR"]}>

                                    <button
                                        className="btn-edit"
                                        onClick={() => onEdit(v)}
                                    >
                                        Editar
                                    </button>

                                </RoleGuard>

                                <RoleGuard roles={["ADMIN"]}>

                                    <button
                                        className="btn-delete"
                                        onClick={() => onDelete(v.id)}
                                    >
                                        Eliminar
                                    </button>

                                </RoleGuard>

                                <RoleGuard roles={["ADMIN", "OPERADOR"]}>

                                    <button
                                        className="btn-select"
                                        onClick={() => onAsignar(v)}
                                    >
                                        Asignar Conductor
                                    </button>

                                </RoleGuard>

                            </div>

                        </td>

                    </tr>

                ))}

            </tbody>

        </Table>

    );

}

export default VehiculoTable;