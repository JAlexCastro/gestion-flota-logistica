import Table from "../../components/Table/Table";
import RoleGuard from "../../components/Auth/RoleGuard";

function FallaTable({ fallas, onEdit, onDelete }) {

    return (

        <Table>

            <thead>
                <tr>
                    <th>Patente</th>
                    <th>Fecha</th>
                    <th>Descripción</th>
                    <th>Prioridad</th>
                    <th>Estado</th>
                    <RoleGuard roles={["ADMIN", "OPERADOR"]}>
                        <th>Acciones</th>
                    </RoleGuard>
                </tr>
            </thead>

            <tbody>

                {fallas.map((f) => (

                    <tr key={f.id}>

                        <td>{f.patente}</td>
                        <td>{f.fecha}</td>
                        <td>{f.descripcion}</td>
                        <td>{f.prioridad}</td>
                        <td>{f.estado}</td>

                        <td>
                            <div className="table-actions">

                                <RoleGuard roles={["ADMIN", "OPERADOR"]}>
                                    <button
                                        className="btn-edit"
                                        onClick={() => onEdit(f)}
                                    >
                                        Editar
                                    </button>
                                </RoleGuard>

                                <RoleGuard roles={["ADMIN"]}>
                                    <button
                                        className="btn-delete"
                                        onClick={() => onDelete(f.id)}
                                    >
                                        Eliminar
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

export default FallaTable;