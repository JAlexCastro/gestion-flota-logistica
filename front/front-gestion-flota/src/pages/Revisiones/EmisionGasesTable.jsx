import Table from "../../components/Table/Table";

function EmisionGasesTable({ emisiones, onEdit, onDelete }) {

    return (
        <Table>

            <thead>
                <tr>
                    <th>ID</th>
                    <th>Patente</th>
                    <th>Emisión</th>
                    <th>Vencimiento</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>

            <tbody>

                {emisiones.map(e => (
                    <tr key={e.id}>
                        <td>{e.id}</td>
                        <td>{e.patente}</td>
                        <td>{e.fechaEmision}</td>
                        <td>{e.fechaVencimiento}</td>
                        <td>{e.estado}</td>

                        <td>
                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(e)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(e.id)}
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

export default EmisionGasesTable;