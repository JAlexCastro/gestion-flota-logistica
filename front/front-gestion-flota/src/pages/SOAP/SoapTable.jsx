import Table from "../../components/Table/Table";

function SoapTable({ soaps, onEdit, onDelete }) {

    return (

        <Table>

            <thead>
                <tr>

                    <th>ID</th>
                    <th>Patente</th>
                    <th>Aseguradora</th>
                    <th>Póliza</th>
                    <th>Inicio</th>
                    <th>Vencimiento</th>
                    <th>Acciones</th>

                </tr>
            </thead>

            <tbody>

                {soaps.map((s) => (

                    <tr key={s.id}>

                        <td>{s.id}</td>
                        <td>{s.patente}</td>
                        <td>{s.aseguradora}</td>
                        <td>{s.numeroPoliza}</td>
                        <td>{s.fechaInicio}</td>
                        <td>{s.fechaVencimiento}</td>

                        <td>

                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(s)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(s.id)}
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

export default SoapTable;