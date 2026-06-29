import Table from "../../components/Table/Table";

function MantencionTable({ mantenciones, onEdit, onDelete }) {

    return (

        <Table>

            <thead>
                <tr>

                    <th>ID</th>
                    <th>Vehículo</th>
                    <th>Fecha</th>
                    <th>Mantenimiento (km)</th>
                    <th>km Faltantes</th>
                    <th>Tipo</th>
                    <th>Taller</th>
                    <th>Acciones</th>

                </tr>
            </thead>

            <tbody>

                {mantenciones.map((m) => (

                    <tr key={m.id}>

                        <td>{m.id}</td>
                        <td>{m.patente}</td>
                        <td>{m.fecha}</td>
                        <td>{m.kilometraje}</td>
                        <td>FALTANTE</td>
                        <td>{m.tipo}</td>
                        <td>{m.taller}</td>

                        <td>

                            <div className="table-actions">

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