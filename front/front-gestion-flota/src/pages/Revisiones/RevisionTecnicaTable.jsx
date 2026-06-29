import Table from "../../components/Table/Table";

function RevisionTecnicaTable({ revisiones, onEdit, onDelete }) {

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

                {revisiones.map(r => (
                    <tr key={r.id}>
                        <td>{r.id}</td>
                        <td>{r.patente}</td>
                        <td>{r.fechaRevision}</td>
                        <td>{r.fechaVencimiento}</td>
                        <td>{r.estado}</td>

                        <td>
                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(r)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(r.id)}
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

export default RevisionTecnicaTable;