import Table from "../../components/Table/Table";

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
                    <th>Acciones</th>
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

                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(f)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(f.id)}
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

export default FallaTable;